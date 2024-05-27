package com.threepounds.caseproject.controller;

import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import com.threepounds.caseproject.controller.dto.AdvertDto;
import com.threepounds.caseproject.controller.dto.DistanceRequestDto;
import com.threepounds.caseproject.controller.dto.UpdatePriceDto;
import com.threepounds.caseproject.controller.mapper.AdvertMapper;
import com.threepounds.caseproject.controller.mapper.PriceMapper;
import com.threepounds.caseproject.controller.resource.AdvertResource;
import com.threepounds.caseproject.controller.resource.CategoryResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.*;
import com.threepounds.caseproject.data.entity.adress.City;
import com.threepounds.caseproject.data.entity.adress.County;
import com.threepounds.caseproject.data.entity.adress.Street;
import com.threepounds.caseproject.exceptions.NotFoundException;
import com.threepounds.caseproject.pdf.PdfGenerator;
import com.threepounds.caseproject.service.*;
import com.threepounds.caseproject.util.S3Util;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.search.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/advert")
public class AdvertController {

  private static final String S3_BUCKET_ADVERT_FOLDER = "advert/";
  private final AdvertService advertService;
  private final AdvertMapper advertMapper;

  private final CategoryService categoryService;

  private final AdvertTagService advertTagService;
  //private final TagService tagService;
  private final AdvertPhotoService advertPhotoService;
  private final CityService cityService;
  private final CountyService countyService;
  private final StreetService streetService;
  private final UserService userService;

  private final PriceMapper priceMapper;


  private final PriceService priceService;


  public AdvertController(AdvertService advertService, AdvertMapper advertMapper,
      CategoryService categoryService, AdvertTagService advertTagService,
      AdvertPhotoService advertPhotoService,
      CityService cityService, CountyService countyService, StreetService streetService,
      UserService userService,
      PriceMapper priceMapper, PriceService priceService) {
    this.advertService = advertService;
    this.advertMapper = advertMapper;
    this.categoryService = categoryService;
    this.advertTagService = advertTagService;
    this.advertPhotoService = advertPhotoService;
    //this.tagService = tagService;
    this.cityService = cityService;
    this.countyService = countyService;
    this.streetService = streetService;
    this.userService = userService;
    this.priceMapper = priceMapper;
    this.priceService = priceService;
  }

  @PostMapping("")
  public ResponseModel<AdvertResource> createAdvert(@RequestBody AdvertDto advertDto,
      Principal principal) {
    User user = userService.getByEmail(principal.getName())
        .orElseThrow(() -> new NotFoundException("User not found"));
    Advert advertToSave = advertMapper.advertDtoToEntity(advertDto);
    advertToSave.setCreatorId(user.getId());
    advertService.save(advertToSave);
    City city = cityService.getById(advertDto.getCityId())
        .orElseThrow(() -> new NotFoundException("City Not Found"));
    County county = countyService.getById(advertDto.getCountyId())
        .orElseThrow(() -> new NotFoundException("County Not Found"));
    Street street = streetService.getById(advertDto.getStreetId())
        .orElseThrow(() -> new NotFoundException("Street Not Found"));

    advertToSave.setCityId(String.valueOf(advertDto.getCityId()));
    advertToSave.setCountyId(String.valueOf(advertDto.getCountyId()));
    advertToSave.setStreetId(String.valueOf(advertDto.getStreetId()));
    List<Tag> tags = new ArrayList<>();
    List<ESTag> esTags = new ArrayList<>();
    advertDto.getTags().forEach(t -> {
      Tag tag = new Tag();
      tag.setTag(t);
      tag.setAdvert(advertToSave);
      tags.add(tag);
      advertTagService.save(tag);
      ESTag esTag = new ESTag();
      esTag.setAdvert(advertToSave);
      esTag.setTag(t);
      esTags.add(esTag);
      //tagService.save(esTag);

    });

    try {
      advertToSave.setTag(tags);
      Category category = categoryService.getById(advertDto.getCategoryId())
          .orElseThrow(() -> new IllegalArgumentException());

      Advert savedAdvert = advertService.save(advertToSave);
      savedAdvert.setCategory(category);
      advertService.save(savedAdvert);
      AdvertResource advertResource = advertMapper.entityToAdvertResource(savedAdvert);
      advertResource.setTags(
          savedAdvert.getTag().stream().map(Tag::getTag).collect(Collectors.toList()));
      return new ResponseModel<>(HttpStatus.OK.value(), advertResource, null);
    } catch (Exception e) {
      log.error("Error", e);
    }
    return new ResponseModel<>(HttpStatus.OK.value(), null, null);
  }

  @DeleteMapping("{id}")
  @CacheEvict(value = "advert", key = "#id")
  public ResponseEntity<String> delete(@PathVariable String id) {
    advertService.delete(id);
    return ResponseEntity.ok("success");
  }

  @GetMapping("/{id}")
  //@Cacheable(value = "advert",key = "#id")
  public ResponseModel<AdvertResource> getOneAdvert(@PathVariable String id) {
    Advert advert = advertService.getById(id)
        .orElseThrow(() -> new IllegalArgumentException());
    advertService.save(advert);
    AdvertResource advertResource = advertMapper.entityToAdvertResource(advert);

    return new ResponseModel<>(HttpStatus.OK.value(), advertResource, null);
  }

  @PutMapping("/{id}")
  @CachePut(value = "advert", key = "#id")
  public ResponseModel<AdvertResource> update(@PathVariable String id,
      @RequestBody AdvertDto advertDto) {
    Advert existingAdvert = advertService.getById(id)
        .orElseThrow(() -> new RuntimeException());
    existingAdvert.setCityId(String.valueOf(advertDto.getCityId()));
    existingAdvert.setCountyId(String.valueOf(advertDto.getCountyId()));
    existingAdvert.setStreetId(String.valueOf(advertDto.getStreetId()));

    Advert mappedAdvert = advertMapper.advertDtoToEntity(advertDto);
    mappedAdvert.setId(existingAdvert.getId());

    Advert updateAdvert = advertService.save(mappedAdvert);
    AdvertResource advertResource = advertMapper.entityToAdvertResource(updateAdvert);
    return new ResponseModel<>(HttpStatus.OK.value(), advertResource, null);

  }

  @GetMapping("")
  public ResponseEntity<List<AdvertResource>> list() {
    List<AdvertResource> advertResources = advertMapper.entityToAdvertResource(
        advertService.getAllAdvert());
    return ResponseEntity.ok(advertResources);
  }

  @GetMapping("/category/{id}")
  public ResponseModel getCategoryWithAdvert(@PathVariable String id) {
    Category category = categoryService.getById(id)
        .orElseThrow(() -> new NotFoundException("Category not found"));

    List<Advert> adverts = advertService.advertsByCategory(category);
    List<AdvertResource> advertResources = advertMapper.entityToAdvertResource(adverts);
    return new ResponseModel<>(HttpStatus.OK.value(), advertResources, null);

  }

  @GetMapping("/page")
  public ResponseModel<List<AdvertResource>> listByPage(@RequestParam int pageNumber,
      @RequestParam int pageSize) {

    Page<Advert> adverts = advertService.listByPage(pageNumber, pageSize);

    List<AdvertResource> advertResources = advertMapper.entityToAdvertResource(
        adverts.toList());

    return new ResponseModel<>(HttpStatus.OK.value(), advertResources, null,
        (int) adverts.getTotalElements(), adverts.getTotalPages());
  }
    /*
    @GetMapping("/es")
    public ResponseModel<Iterable<ESTag>> getAll(){
        Iterable<ESTag> esTags=tagService.getAllTags();
        return new ResponseModel<>(HttpStatus.OK.value(),esTags,null);
    }
    @PostMapping("/es/search")
    public  ResponseModel<List<ESTag>> searchAdvertWithTag(@RequestBody EsDto esDto){
              List<ESTag>esTags= tagService.searchAdvert(esDto);
        return new ResponseModel<>(HttpStatus.OK.value(),esTags,null);
    }
*/

  // TODO , put mapping endpoint , ilanın gösterilme sayısını artırsın
  @PutMapping("/display/{id}")
  @CachePut(value = "advert", key = "#id")
  public Integer displayCount(@PathVariable String id) {
    Advert existingAdvert = advertService.getById(id)
        .orElseThrow(() -> new RuntimeException());
    if (existingAdvert.getCounter() == null) {
      existingAdvert.setCounter(0);
    }
    System.out.println(existingAdvert.getCounter());

    return existingAdvert.getCounter();

  }

  @PutMapping("/distance")
  public ResponseEntity<List<AdvertResource>> searchByDistance(
      @RequestBody DistanceRequestDto distanceRequest) {

    List<AdvertResource> advertResources = advertMapper.entityToAdvertResource(
        advertService.getAllAdvert());
    for (AdvertResource resource : advertResources) {
      double distance = advertService.findDistance(distanceRequest.getLatitude(),
          resource.getLatitude().doubleValue(), distanceRequest.getLongitude(),
          resource.getLongitude().doubleValue());
      resource.setDistance(distance);
    }

    // sort by distance
    Collections.sort(advertResources, (o1, o2) -> (int) (o1.getDistance() - o2.getDistance()));

    return ResponseEntity.ok(advertResources);

  }

  @PutMapping("/photos")
  public ResponseModel<String> uploadPhotos(@RequestParam("files") MultipartFile[] files,
      @RequestParam("id") String advertId) {

    List<String> fileKeys = new ArrayList<>();
    Arrays.asList(files).stream().forEach(file -> {
      // TODO create key and add to the S3Util.uploadObject
      UUID s3id = UUID.randomUUID();

      try {
        S3Util.uploadObject("3pounds-traning", S3_BUCKET_ADVERT_FOLDER + s3id + ".jpg",
            file.getInputStream());
      } catch (IOException e) {
        log.error("An error occured during file upload", e);
      }
      // TODO add to fileKeys
      fileKeys.add(s3id.toString());

    });

    // TODO update advert_photos entity by fileKeys and save
    Advert existingAdvert = advertService.getById(advertId)
        .orElseThrow(() -> new RuntimeException());
    advertService.save(existingAdvert);
    List<Photo> photos = new ArrayList<>();
    for (String key : fileKeys) {
      Photo photo = new Photo();
      photo.setAdvert(existingAdvert);
      photo.setKeys(key);
      advertPhotoService.save(photo);
      photos.add(photo);
    }
    existingAdvert.setPhotos(photos);
    advertService.save(existingAdvert);

    return new ResponseModel<>(HttpStatus.OK.value(), "Photos Uploaded", null);
  }


  @GetMapping("/most-viewed")
  public ResponseModel<List<AdvertResource>> mostWieved() {

    Page<Advert> adverts = advertService.listByPageSort(0, 10, Sort.by("counter").descending());

    List<AdvertResource> advertResources = advertMapper.entityToAdvertResource(
        adverts.toList());

    return new ResponseModel<>(HttpStatus.OK.value(), advertResources, null);
  }

  @PutMapping("/update-price")
  public ResponseModel<AdvertResource> updatePrice(@RequestBody UpdatePriceDto updatePriceDto) {
    Advert existingAdvert = advertService.getById(updatePriceDto.getAdvertId())
        .orElseThrow(() -> new RuntimeException());

    if (existingAdvert.getPrice().doubleValue() == updatePriceDto.getPrice().doubleValue()) {
      System.out.println("Ücretler aynıdır");
    }

    PriceHistory priceHistory = new PriceHistory();
    priceHistory.setOldPrice(existingAdvert.getPrice());
    priceHistory.setNewPrice(updatePriceDto.getPrice());
    priceHistory.setAdvert_id(String.valueOf(updatePriceDto.getAdvertId()));
    priceService.save(priceHistory);
    existingAdvert.setPrice(updatePriceDto.getPrice());
    Advert updateAdvert = advertService.save(existingAdvert);
    AdvertResource advertResource = advertMapper.entityToAdvertResource(updateAdvert);

    return new ResponseModel<>(HttpStatus.OK.value(), advertResource, null);


  }

  @GetMapping("/price-increased")
  public ResponseEntity<List<PriceHistory>> increasedPrice() {
    List<PriceHistory> priceResources = priceService.getAllPrice();
    List<PriceHistory> IncreasedAdverts = priceResources.stream()
        .filter(t -> t.getNewPrice().doubleValue() > t.getOldPrice().doubleValue())
        .collect(Collectors.toList());
    return ResponseEntity.ok(IncreasedAdverts);
  }

  @GetMapping("/price-decreased")
  public ResponseEntity<List<PriceHistory>> decreasedPrice() {
    List<PriceHistory> priceResources = priceService.getAllPrice();
    List<PriceHistory> DecreasedAdverts = priceResources.stream()
        .filter(t -> t.getNewPrice().doubleValue() < t.getOldPrice().doubleValue()).
        collect(Collectors.toList());
    return ResponseEntity.ok(DecreasedAdverts);
  }


  @GetMapping("/export/csv/{id}")
  public void exportCsv(HttpServletResponse response, @PathVariable String id) throws IOException {
    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", "attachment; file=adverts.csv");

    Category category = categoryService.getById(id)
        .orElseThrow(() -> new NotFoundException("Category not found"));

    List<Advert> adverts = advertService.advertsByCategory(category);
    List<AdvertResource> advertResources = advertMapper.entityToAdvertResource(adverts);


    CSVWriter csvContent = new CSVWriter(response.getWriter());
    csvContent.writeNext(
        new String[]{"id", "name", "description", "price", "city_name", "county_name",
            "street_name"});

    for (AdvertResource advertResource : advertResources) {

      csvContent.writeNext(new String[]{advertResource.getId(), advertResource.getTitle(),
          advertResource.getDescription(), advertResource.getPrice().toString(),
          advertResource.getCity().getName(), advertResource.getCounty().getName(),
          advertResource.getStreet().getName()});

      csvContent.close();

    }
  }

  @GetMapping("/export/pdf/{id}")
  public void exportPdf(HttpServletResponse response, @PathVariable String id)
      throws DocumentException, IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; file=adverts.pdf");

    Category category = categoryService.getById(id)
        .orElseThrow(() -> new NotFoundException("Category not found"));

    List<Advert> adverts = advertService.advertsByCategory(category);
    List<AdvertResource> advertResources = advertMapper.entityToAdvertResource(adverts);

    PdfGenerator.generate(response,advertResources);

  }
}
