package com.crudmadtek.demo.models;


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
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("seeker")
public class Seeker extends  User{

    @DBRef(lazy = true)
    @JsonManagedReference
    private HashSet<Job> listofJob ;




private String path_resume ;
    public Seeker(String username, String email, String password, String location, Set<UserRole> roles) {
        super(username, email, password, location, roles);
        this.listofJob=new HashSet<Job>();
    }
}
