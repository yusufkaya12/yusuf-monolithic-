package com.threepounds.caseproject.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.w3c.dom.Text;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messaging {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String text;
    @Column
    private Boolean enabled;
    @Column
    private String senderId;
    @Column
    private String receiverId;
    @CreationTimestamp
    @Column
    private ZonedDateTime messageTimeStamp;




}
