package com.threepounds.caseproject.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.sql.Blob;
import java.util.UUID;

@Entity(name = "photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String keys;

    @ManyToOne
    @JoinColumn(name = "advert_id", referencedColumnName = "id")
    private Advert advert;
}