package com.threepounds.caseproject.controller.resource;

import com.threepounds.caseproject.data.entity.adress.County;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResource {
  private String id;

  private String name;

  private List<CountyResource> counties;

}
