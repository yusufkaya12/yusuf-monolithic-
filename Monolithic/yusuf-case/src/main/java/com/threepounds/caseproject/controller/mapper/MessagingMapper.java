package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.MessagingDto;
import com.threepounds.caseproject.controller.resource.MessagingResource;
import com.threepounds.caseproject.data.entity.Messaging;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.security.Principal;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MessagingMapper {


   public abstract Messaging messagingDtoToEntity(MessagingDto messagingDto);
   public abstract MessagingResource ResourceToMessagingDto(Messaging messaging);
   public abstract List<MessagingResource> messagingToResourceList(List<Messaging> messaging);

}

