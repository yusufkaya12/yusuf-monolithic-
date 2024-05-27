package com.threepounds.caseproject.data.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "advert_price_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column
  private String advert_id;
  @Column
  private BigDecimal oldPrice;
  @Column
  private BigDecimal newPrice;

}
