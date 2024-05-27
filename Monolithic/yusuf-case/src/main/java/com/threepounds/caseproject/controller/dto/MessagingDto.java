package com.threepounds.caseproject.controller.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MessagingDto {

    private String id;
    private String text;
    private String receiverId;


}
