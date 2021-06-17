package com.crudmadtek.demo.controllers;


import com.crudmadtek.demo.models.Job;
import com.crudmadtek.demo.models.Seeker;
import com.crudmadtek.demo.models.User;
import com.crudmadtek.demo.payload.request.JobRequest;
import com.crudmadtek.demo.payload.request.RecruiterRequest;
import com.crudmadtek.demo.payload.request.SeekerRequest;
import com.crudmadtek.demo.payload.response.DataResponse;
import com.crudmadtek.demo.payload.response.MessageResponse;
import com.crudmadtek.demo.payload.response.SeekerResponse;
import com.crudmadtek.demo.repositories.UserRepository;
import com.crudmadtek.demo.services.RecruiterService;
import com.crudmadtek.demo.services.SeekerService;
import com.crudmadtek.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/recruiter")


public class RecruiterController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RecruiterService recruiterService;

    @PutMapping("/edit")
    public ResponseEntity editprofileSeeker(@RequestBody RecruiterRequest editRequest) {
        try {
            System.out.println("hello edit");
            recruiterService.editprofileRecruiter(editRequest);
            return ResponseEntity.ok(new MessageResponse("recruiter profile updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteUser(@RequestParam String userId) {
        System.out.println("hello delete");

        return ResponseEntity.ok(new DataResponse(userService.deleteUser(userId)));
    }

    @PostMapping(value = "/job/post")
    public ResponseEntity POSTJob(@RequestBody JobRequest jobRequest,@RequestHeader (name="Authorization") String token) {
System.out.println("hello world");
//
        try {
            System.out.println("hello post job");
            System.out.println("hello:"+jobRequest.getSeniority_level());
            recruiterService.POSTJob(jobRequest,token);

            return ResponseEntity.ok(new MessageResponse("recruiter posted a job "));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }



    @DeleteMapping(value = "/job/delete")
    public ResponseEntity deleteJob(@RequestParam String jobid) throws Exception {
        System.out.println("hello delete job");

        return ResponseEntity.ok(new DataResponse(recruiterService.deleteJob(jobid)));
    }



    @GetMapping("/jobs/get")
    public ResponseEntity SearchJobRequest(@RequestHeader  (name="Authorization") String token) throws Exception {
        try {
            HashSet<Job> result=recruiterService.FetchAllJobs(token);
            return ResponseEntity.ok(new DataResponse(result));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }


    @GetMapping("/seeker/get")
    public ResponseEntity SearchSeekerRequest(@RequestHeader  (name="Authorization") String token,@RequestParam String idofjob) throws Exception {
        try {
            HashSet<SeekerResponse> result=recruiterService.FetchAllSeeker(token,idofjob);
            return ResponseEntity.ok(new DataResponse(result));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }


    @PutMapping("/job/edit")
    public ResponseEntity editJob(@RequestBody JobRequest editRequest,@RequestHeader  (name="Authorization") String token) {
        try {
            System.out.println("hello edit");
            recruiterService.editJob(editRequest,token);
            return ResponseEntity.ok(new MessageResponse("recruiter job updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }



    @PostMapping("/downloadFile")
    public ResponseEntity test(@RequestBody String uploadDir)  {
        try {
            System.out.println(uploadDir);
           Resource resource= recruiterService.loadFileAsResource(uploadDir);
           String contentType = "multipart/form-data";
           return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                   .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}