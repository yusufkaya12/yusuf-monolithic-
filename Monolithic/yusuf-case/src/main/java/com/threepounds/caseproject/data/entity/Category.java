package com.threepounds.caseproject.data.entity;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private boolean active;






}
