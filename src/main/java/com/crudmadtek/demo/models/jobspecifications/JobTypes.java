package com.crudmadtek.demo.models.jobspecifications;


import lombok.Getter;

@Getter
public enum JobTypes {

    full_time("1"),part_time("2"),contract("3"),voluntary("4");

private  String number ;
    JobTypes(String number) {
        this.number=number ;
                         }


}
