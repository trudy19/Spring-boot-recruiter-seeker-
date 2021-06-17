package com.crudmadtek.demo.repositories;


import com.crudmadtek.demo.models.Job;
import com.crudmadtek.demo.models.Jobindeed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndeedRepository  extends MongoRepository<Jobindeed,String> {

}
