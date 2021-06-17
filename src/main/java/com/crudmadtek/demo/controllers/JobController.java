package com.crudmadtek.demo.controllers;


import com.crudmadtek.demo.models.Job;
import com.crudmadtek.demo.models.Jobindeed;
import com.crudmadtek.demo.payload.request.JobRequest;
import com.crudmadtek.demo.payload.request.RecruiterRequest;
import com.crudmadtek.demo.payload.response.DataResponse;
import com.crudmadtek.demo.payload.response.MessageResponse;
import com.crudmadtek.demo.services.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/job")
@Slf4j
public class JobController {

@Autowired
JobService jobService ;

    @GetMapping("/get")
    public ResponseEntity SearchJobRequest() {
        try {
            List<Job> result=jobService.FetchAllJobs();
            return ResponseEntity.ok(new DataResponse(result));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }
    @GetMapping("/get/indeed")
    public ResponseEntity SearchJobindeedrequest() {
        try {
            List<Jobindeed> result=jobService.FetchAllJobsIndeed();
            return ResponseEntity.ok(new DataResponse(result));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }
}
