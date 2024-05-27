package com.threepounds.caseproject.controller.resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagingResource {
    private String id;
    private String text;
    private Boolean enabled;
    private String receiverId;
    private String senderId;
    private ZonedDateTime messageTimeStamp;
}
