package com.threepounds.caseproject.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;
@Data
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String username;
    @Column
    private String name;
    @Column
    @NotNull
    private String email;

    @Column
    private String password;

    @Column
    private String avatar;

    @Column
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany
    private List<Favourites> favourites;


}

