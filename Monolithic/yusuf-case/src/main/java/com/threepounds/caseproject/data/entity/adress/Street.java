package com.threepounds.caseproject.data.entity.adress;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "street")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Street {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column
  private String name;
  @Column
  private String county_id;





}
