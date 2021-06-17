package com.crudmadtek.demo.models;

import lombok.Getter;


@Getter


public enum UserRole {
     RECRUITER("recruiter"),SEEKER("seeker") ;

    @Getter
    private String name;

    UserRole(String name) {
        this.name = name;
    }


    public static UserRole asRole(String str) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(str))
                return role;
        }
        return null;
    }


}
