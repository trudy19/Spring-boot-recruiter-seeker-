package com.crudmadtek.demo.controllers;


import com.crudmadtek.demo.models.Job;
import com.crudmadtek.demo.models.Jobindeed;
import com.crudmadtek.demo.models.User;
import com.crudmadtek.demo.payload.request.LoginRequest;
import com.crudmadtek.demo.payload.request.RecruiterRequest;
import com.crudmadtek.demo.payload.request.SeekerRequest;
import com.crudmadtek.demo.payload.response.DataResponse;
import com.crudmadtek.demo.payload.response.MessageResponse;
import com.crudmadtek.demo.services.RecruiterService;
import com.crudmadtek.demo.services.SeekerService;
import com.crudmadtek.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    RestTemplate restTemplate = new RestTemplate();
//http://127.0.0.1:5000/scrape
String fooResourceUrl
        = "http://127.0.0.1:5000/run";


    @Autowired
    PasswordEncoder encoder;

@Autowired
RecruiterService recruiterService ;
@Autowired
    SeekerService seekerService ;

    @Autowired
    UserService userService ;

    @PostMapping(value = "/recruiter/signup")
    public ResponseEntity registerRecruiter(@RequestBody @Validated RecruiterRequest signUpRequest) {
        try {

         User recruiter=   recruiterService.createUser(signUpRequest);
           // return ResponseEntity.ok(new DataResponse(recruiter));

           return ResponseEntity.ok(new MessageResponse("Recruiter registered successfully!"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


    @PostMapping(value = "/seeker/signup")
    public ResponseEntity registerSeeker(@RequestBody @Validated SeekerRequest signUpRequest) {
        try {
            System.out.println("hello Seeker");
         User u=   seekerService.createUser(signUpRequest);
          //  User u = userService.authenticateUser();
          //  return ResponseEntity.ok(new DataResponse(u));
            System.out.println("hello seeker");
            System.out.println("email   :"+signUpRequest.getEmail());
            System.out.println("username   :"+signUpRequest.getUsername());
            System.out.println("password   :"+signUpRequest.getPassword());
            System.out.println("location   :"+signUpRequest.getLocation());
            return ResponseEntity.ok(new MessageResponse("seeker registered successfully!"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


    @PostMapping(value = "/signin")
    public ResponseEntity authenticateSeeker(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("usernaeme::::::"+loginRequest.getUsername()+loginRequest.getPassword()+"emailllll:::::"+loginRequest.getEmail());
            User u = userService.authenticateUser(loginRequest);
            return ResponseEntity.ok( new DataResponse(u));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }




//
//    @PostMapping(value = "/test")
//    public ResponseEntity test() {
//        try {
//            System.out.println();
//            ResponseEntity<String> response
//                    = restTemplate.getForEntity(fooResourceUrl , String.class);
//
//            //String data = "[{\"userName\": \"sandeep\",\"age\":30},{\"userName\": \"vivan\",\"age\":5}]  ";
//            JSONObject jsnobject = new JSONObject(response.getBody());
//            JSONArray jsonArray = jsnobject.getJSONArray("data");
//           HashSet<Jobindeed> l=new HashSet<Jobindeed>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject explrObject = jsonArray.getJSONObject(i);
//                l.add(new Jobindeed(explrObject.get("title").toString(),explrObject.get("location").toString()));
//            }




            //            JsonObject jsnobject = new JsonObject(response.getBody());
//            String jsonArray = jsnobject.getJson("")
          //  JSONArray jsonArr = new JsonArray(response.getBody());

//           Gson g = new Gson();
//            Job dto = g.fromJson(response.getBody(), Job.class);
          //   Job student = new ObjectMapper().readValue((DataInput) response, Job.class);
//
//            JSONParser parser = new JSONParser();
//            JSONObject json = (JsonObject) parser.parse(response);

//            GsonJsonParser g = new GsonJsonParser();
//            List r =g.parseList(response.getBody());
           // System.out.println(dto.getLocation());

//            return ResponseEntity.ok(new MessageResponse("seeker registered successfully!"));
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
//        }
//    }






}
