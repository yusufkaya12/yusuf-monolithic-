package com.threepounds.caseproject.data.entity.adress;

import com.threepounds.caseproject.controller.resource.CountyResource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity(name = "county")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class County {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column
  private String name;
  @Column
  private String city_id;

  @OneToMany
  private List<Street> streets;

}
