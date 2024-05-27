package com.threepounds.caseproject.controller;

import com.threepounds.caseproject.controller.dto.UserDto;
import com.threepounds.caseproject.controller.mapper.UserMapper;
import com.threepounds.caseproject.controller.resource.UserResource;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.Role;
import com.threepounds.caseproject.data.entity.User;
import com.threepounds.caseproject.exceptions.NotFoundException;
import com.threepounds.caseproject.service.RoleService;
import com.threepounds.caseproject.service.UserService;
import com.threepounds.caseproject.util.S3Util;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final String S3_BUCKET_USER_FOLDER = "user/";

    private final UserMapper userMapper;
    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserMapper userMapper, UserService userService, RoleService roleService,
                          PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @PreAuthorize("hasAuthority('CREATE_USERS')")
    @PostMapping("")
    public ResponseEntity<UserResource> createUser(@RequestBody UserDto userDto) {
        User userToSave = userMapper.userDtoToEntity(userDto);
        List<Role> roles = roleService.list(userDto.getRoles());
        userToSave.setRoles(roles);
        userToSave.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userService.saveUser(userToSave);
        UserResource userResource = userMapper.userDto(savedUser);

        return ResponseEntity.ok(userResource);
    }

    @PreAuthorize("hasAuthority('VIEW_USERS')")
    @GetMapping("{id}")
    public ResponseEntity<UserResource> getOneUser(@PathVariable String id) {
        User user = userService.getByUserId(id)
                .orElseThrow(() -> new RuntimeException());
        UserResource userResource = userMapper.userDto(user);
        return ResponseEntity.ok(userResource);
    }

    @PreAuthorize("hasAuthority('DELETE_USERS')")

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteOneUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Success");
    }

    @PreAuthorize("hasAuthority('EDIT_USERS')")
    @PutMapping("{userId}")
    public ResponseEntity<UserResource> updateOneUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        User existingUser = userService.getByUserId(userId)
                .orElseThrow(() -> new RuntimeException());
        User mappedUser = userMapper.userDtoToEntity(userDto);
        List<Role> roles = roleService.list(userDto.getRoles());
        mappedUser.setRoles(roles);
        mappedUser.setId(existingUser.getId());
        User updateUser = userService.saveUser(mappedUser);
        UserResource userResource = userMapper.userDto(updateUser);
        return ResponseEntity.ok(userResource);
    }

    @PreAuthorize("hasAuthority('VIEW_USERS')")
    @GetMapping("")
    public ResponseEntity<List<UserResource>> list() {
        List<UserResource> userResources = userMapper.userDtoToList(
                userService.list());
        return ResponseEntity.ok(userResources);
    }

    @GetMapping("/page")
    public ResponseModel<List<UserResource>> listByPage(@RequestParam int pageNumber, @RequestParam int pageSize) {

        Page<User> users = userService.listByPage(pageNumber, pageSize);

        List<UserResource> categoryResources = userMapper.userDtoToList(
                users.toList());

        return new ResponseModel<>(HttpStatus.OK.value(), categoryResources, null,
                (int) users.getTotalElements(), users.getTotalPages());
    }

    //avatar ekleyen controller
    @PutMapping("/avatar")
    public ResponseModel<String> uploadAvatar(@RequestParam("file") MultipartFile file,
                                              @RequestParam("id") String userId) throws IOException {
        User existingUser = userService.getByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        String fileKey = UUID.randomUUID().toString();


        S3Util.uploadObject("3pounds-traning", S3_BUCKET_USER_FOLDER + fileKey + ".jpg", file.getInputStream());


        existingUser.setAvatar(fileKey);
        userService.saveUser(existingUser);

        return new ResponseModel<>(HttpStatus.OK.value(), "Avatar Uploaded", null);
    }


}
