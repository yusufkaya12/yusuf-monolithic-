package com.threepounds.caseproject.controller.resource;

import com.threepounds.caseproject.data.entity.Advert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResource {
  private String id;
  private String name;
  private String description;
   private Set<Advert> adverts;








}
