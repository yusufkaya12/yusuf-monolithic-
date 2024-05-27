package com.threepounds.caseproject.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "codes")
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String userId;
    @Column
    private String otp;
    @Column
    private boolean isUsed;
}
