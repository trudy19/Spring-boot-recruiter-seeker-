package com.crudmadtek.demo.models;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashSet;
import java.util.Set;


@Document(collection = "users")

@Getter
@Setter
@TypeAlias("recruiter")
@AllArgsConstructor
@NoArgsConstructor

public class Recruiter extends User {
    @DBRef
    @JsonManagedReference
    private HashSet<Job> listofjob ;

    public Recruiter(String username, String email, String password, String location, Set<UserRole> roles) {

        super(username,email,password,location,roles);

        this.listofjob=new HashSet<>();
    }
}
