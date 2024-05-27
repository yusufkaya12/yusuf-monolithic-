package com.threepounds.caseproject.data.entity.adress;

import com.threepounds.caseproject.data.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column
  private String name;


  @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
  private List<County> counties;


}
