package com.threepounds.caseproject.controller.mapper;

import com.threepounds.caseproject.controller.dto.UserDto;
import com.threepounds.caseproject.controller.resource.UserResource;
import com.threepounds.caseproject.data.entity.User;
import com.threepounds.caseproject.security.auth.SignUpRequest;
import com.threepounds.caseproject.util.S3Util;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public abstract class UserMapper {

    private static final String S3_BUCKET_ADVERT_FOLDER = "user/";
    @Mapping(source = "roles" , target = "roles", ignore = true)
    public abstract User userDtoToEntity(UserDto userDto);

    public abstract UserResource userDto(User user);
    @Mapping(source = "roles" , target = "roles", ignore = true)
    public abstract List<UserResource> userDtoToList(List<User> user);



    public abstract User userDtoToEntity(SignUpRequest signUpRequest);

    @AfterMapping
    void afterResourceMapping(User entity, @MappingTarget UserResource resource) {

        String avatarUrl = S3Util.getPreSignedUrl("3pounds-traning", S3_BUCKET_ADVERT_FOLDER + entity.getAvatar() + ".jpg");
        resource.setAvatar(avatarUrl);

    }

}
