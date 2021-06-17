package com.crudmadtek.demo.models;



import com.crudmadtek.demo.models.jobspecifications.JobTitles;
import com.crudmadtek.demo.models.jobspecifications.JobTypes;
import com.crudmadtek.demo.models.jobspecifications.SeniorityLevel;
import com.crudmadtek.demo.models.jobspecifications.Skills;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

@Document("JOB")
@TypeAlias("JOB")
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class Job  {
    @Id
    private String id ;
    private String description ;
    private JobTitles title ;
    private String location ;
    private JobTypes type ;
    private SeniorityLevel seniority_level ;
    private Skills skill_required ;


    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createdAt ;

    @DBRef
    @JsonBackReference
    private Recruiter recruiter ;

    @DBRef(lazy = true)
    @JsonBackReference
    private HashSet<Seeker> listofseeker ;

    public Job(JobTitles title, String description,String location, JobTypes type, SeniorityLevel seniority_level, Skills skill_required,Recruiter recruiter) {
       // this.id="60b647bd86e88a54e3b5cf4d";
        this.title = title;
        this.description=description ;
        this.location = location;
        this.type = type;
        this.seniority_level = seniority_level;
        this.skill_required = skill_required;
        this.recruiter=recruiter;
        this.listofseeker=new HashSet<>();
       // HH:mm:ss
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        this.createdAt= format.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return Objects.equals(getId(), job.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static  Job findingjob(String idofjob, HashSet<Job> JOBS) {
        for (Job job : JOBS) {
        if (job.getId().equals(idofjob)) {
            return job;
        }
    }
        return null;
    }

}
