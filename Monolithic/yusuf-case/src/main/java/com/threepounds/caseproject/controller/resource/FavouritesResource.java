package com.threepounds.caseproject.controller.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouritesResource {
  private String id;

  private String userId;

  private String advertId;
  private ZonedDateTime favouritedDate;


}
