package com.crudmadtek.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;


@Document(collection = "users")
@TypeAlias("user")
@JsonIgnoreProperties({"password","resetPasswordToken","confirmationToken","resetPasswordTokenExpiration","resetPasswordTokenExpiration"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String username;
    private String email;
    private String password;
    private String location;
    private Set<UserRole> roles;

    private String resetPasswordToken;
    private String confirmationToken;

    private boolean active;
    private Date resetPasswordTokenExpiration;

    @Transient
    private String token;

    public User(String username, String email, String password, String location, Set<UserRole> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
        this.roles = roles;
    }

}
