package com.crudmadtek.demo.payload.request;


import com.crudmadtek.demo.models.Recruiter;
import com.crudmadtek.demo.models.Seeker;
import com.crudmadtek.demo.models.jobspecifications.JobTitles;
import com.crudmadtek.demo.models.jobspecifications.JobTypes;
import com.crudmadtek.demo.models.jobspecifications.SeniorityLevel;
import com.crudmadtek.demo.models.jobspecifications.Skills;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class JobRequest {


    private String id;
    private String description ;
    private String recruiterid ;
    @NotBlank
    private JobTitles title ;
    @NotBlank

    private String location ;
    @NotBlank

    private JobTypes type ;
    @NotBlank

    private SeniorityLevel seniority_level ;
    @NotBlank
    private Skills skill_required ;







}
