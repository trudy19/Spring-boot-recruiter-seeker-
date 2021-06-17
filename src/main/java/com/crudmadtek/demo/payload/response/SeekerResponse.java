package com.crudmadtek.demo.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeekerResponse {

    private String id;
    private String username;
    private String email;
    private String path_resume ;

    public SeekerResponse(String id, String username, String email, String path_resume) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.path_resume = path_resume;
    }
}
