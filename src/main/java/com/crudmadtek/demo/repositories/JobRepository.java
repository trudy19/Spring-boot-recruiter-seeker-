package com.crudmadtek.demo.repositories;

import com.crudmadtek.demo.models.Job;
import com.crudmadtek.demo.models.Recruiter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<Job,String> {


}
