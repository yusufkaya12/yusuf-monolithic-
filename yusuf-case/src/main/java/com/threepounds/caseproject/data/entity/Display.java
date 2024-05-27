package com.threepounds.caseproject.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "display")
@NoArgsConstructor
@AllArgsConstructor
public class Display {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String advertId;
    @Column
    private Integer counter = 0;

}

