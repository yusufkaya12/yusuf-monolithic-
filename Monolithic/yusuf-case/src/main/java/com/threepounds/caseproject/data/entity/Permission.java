package com.threepounds.caseproject.data.entity;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "permission")
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column
  private String name;

  @Column
  private String displayName;
  @ManyToMany(fetch = FetchType.LAZY,mappedBy = "permissions")
  private List<Role> roles;


  // TODO ManyToOne  1 den fazla permissions 1 role ait olabilir


}
