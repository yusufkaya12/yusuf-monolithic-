package com.threepounds.caseproject.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "favourites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String userId;
    @Column
    private String advertId;
    @Column
    @CreationTimestamp
    private ZonedDateTime favouritedDate;



}
