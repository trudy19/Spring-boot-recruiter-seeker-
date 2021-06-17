package com.crudmadtek.demo.models.jobspecifications;


import lombok.Getter;
// software , Finance , Stock Market, Salesforce, SEO, Growth Hacking
@Getter
public enum Skills {
    software("1"),finance("2"),stock_market("3"),salesforce("4"),seo("5"),growth_hacking("6");
    private String number ;
    Skills(String number){
        this.number=number;
    }
}
