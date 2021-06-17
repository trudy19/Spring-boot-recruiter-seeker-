package com.crudmadtek.demo.models.jobspecifications;


import lombok.Getter;
//Sales Representative, Software Developer, Financial Analyst
@Getter
public enum JobTitles {

    Sales_Representative("1"),Software_Developer("2"),Financial_Analyst("3") ;

    private  String number ;

    JobTitles(String number) {
        this.number=number ;
    }

//    public static JobTitles asJobTitles(int number){
//        for (JobTitles jobtitle : JobTitles.values() )
//        {
//            if(jobtitle.number==number)
//            {
//                return jobtitle;
//            }
//        }
//        return null ;
//    }

}
