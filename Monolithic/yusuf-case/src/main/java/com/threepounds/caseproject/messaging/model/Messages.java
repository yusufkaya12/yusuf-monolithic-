package com.threepounds.caseproject.messaging.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Messages implements Serializable{

    private String id;

    private String name;

    private String content;

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

