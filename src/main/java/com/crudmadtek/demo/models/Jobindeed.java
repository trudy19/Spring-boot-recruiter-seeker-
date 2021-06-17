package com.crudmadtek.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("JOBI")
@TypeAlias("JOB")
@NoArgsConstructor

@Getter
@Setter
public class Jobindeed {


    public Jobindeed(String title, String location, String description, String link_url) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.link_url = link_url;
    }

    private String title ;
    private String location ;
    private String description;
    private String link_url ;
}
