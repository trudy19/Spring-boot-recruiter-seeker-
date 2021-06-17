package com.crudmadtek.demo.models.jobspecifications;


import lombok.Getter;

@Getter
public enum SeniorityLevel {

    junior("1"),senior("2"),entry("3"),associate("4");
    private String number ;
    SeniorityLevel(String number ){
        this.number=number;
    }
}
