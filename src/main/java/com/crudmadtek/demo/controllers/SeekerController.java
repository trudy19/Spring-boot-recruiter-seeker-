package com.crudmadtek.demo.controllers;


import com.crudmadtek.demo.models.Job;
import com.crudmadtek.demo.models.Seeker;
import com.crudmadtek.demo.models.User;
import com.crudmadtek.demo.payload.request.JobRequest;
import com.crudmadtek.demo.payload.request.SeekerRequest;
import com.crudmadtek.demo.payload.response.DataResponse;
import com.crudmadtek.demo.payload.response.MessageResponse;
import com.crudmadtek.demo.repositories.UserRepository;
import com.crudmadtek.demo.services.FileUploadUtil;
import com.crudmadtek.demo.services.RecruiterService;
import com.crudmadtek.demo.services.SeekerService;
import com.crudmadtek.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;

@RestController
@RequestMapping("/seeker")
@CrossOrigin(origins = "*", maxAge = 3600)

public class SeekerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    SeekerService seekerService ;


    @PutMapping("/prof/edit")
    public ResponseEntity editprofileSeeker(@RequestBody SeekerRequest editRequest ) {
        try {
            Seeker seeker = seekerService.editprofileSeeker(editRequest);
            return ResponseEntity.ok(new MessageResponse("client profile updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }


    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteUser(@RequestParam String userId){
        return ResponseEntity.ok(new DataResponse(userService.deleteUser(userId)));
    }




    @PostMapping(value = "/apply")
    public ResponseEntity Applyjob(@RequestParam("file") MultipartFile file,@RequestParam("idofjob")String idofjob, @RequestHeader (name="Authorization") String token) throws Exception {
try{
        seekerService.applyForJob(token,file,idofjob);
        return ResponseEntity.ok(new MessageResponse("donebro"));}
catch (Exception e) {
    return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
}
    }




    @GetMapping("/job/get")
    public ResponseEntity SearchJobRequest(@RequestHeader (name="Authorization") String token) throws Exception {
        try {
            HashSet<Job> result=seekerService.FetchAllJobs(token);
            return ResponseEntity.ok(new DataResponse(result));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

    }

}
