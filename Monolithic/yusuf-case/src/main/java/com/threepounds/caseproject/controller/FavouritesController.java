package com.threepounds.caseproject.controller;

import com.threepounds.caseproject.controller.dto.FavouritesDto;
import com.threepounds.caseproject.controller.mapper.FavouritesMapper;
import com.threepounds.caseproject.controller.resource.FavouritesResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.Favourites;
import com.threepounds.caseproject.data.entity.User;
import com.threepounds.caseproject.exceptions.NotFoundException;
import com.threepounds.caseproject.service.FavouriteService;
import com.threepounds.caseproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/favourites")
public class FavouritesController {
    private final FavouritesMapper favouritesMapper;
    private final FavouriteService favouriteService;

    private final UserService userService;

    public FavouritesController(FavouritesMapper favouritesMapper, FavouriteService favouriteService,
                                UserService userService) {
        this.favouritesMapper = favouritesMapper;
        this.favouriteService = favouriteService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseModel<FavouritesResource> createFavourite(@RequestBody FavouritesDto favouritesDto,
                                                             Principal principal) {
        Favourites favourites = favouritesMapper.favouriteDtoToEntity(favouritesDto);
        User user = userService.getByEmail(principal.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));
        favourites.setUserId(user.getId());
        favouriteService.save(favourites);
        FavouritesResource favouritesResource = favouritesMapper.entityToFavouriteResource(favourites);

        return new ResponseModel<>(HttpStatus.OK.value(), favouritesResource, null);
    }

    @GetMapping("")
    public ResponseEntity<List<FavouritesResource>> list() {
        List<FavouritesResource> favouritesResources = favouritesMapper.entityToFavouriteResources(
                favouriteService.getAllFavourites());

        return ResponseEntity.ok(favouritesResources);

    }


    @DeleteMapping("/{advertId}")
    public ResponseEntity<String> delete(@PathVariable String advertId) {
        favouriteService.delete(advertId);
        return ResponseEntity.ok("success");
    }


}
