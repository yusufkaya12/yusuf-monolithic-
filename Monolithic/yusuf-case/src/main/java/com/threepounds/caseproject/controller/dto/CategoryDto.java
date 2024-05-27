package com.threepounds.caseproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

  private String id;
  private String name;
  private String description;
  private boolean active;



}
