package com.crudmadtek.demo.services;


import com.crudmadtek.demo.models.*;
import com.crudmadtek.demo.payload.request.LoginRequest;
import com.crudmadtek.demo.payload.request.RecruiterRequest;
import com.crudmadtek.demo.payload.request.SeekerRequest;
import com.crudmadtek.demo.repositories.JobRepository;
import com.crudmadtek.demo.repositories.SeekerRepository;
import com.crudmadtek.demo.repositories.UserRepository;
import com.crudmadtek.demo.security.jwt.JwtUtils;
import com.crudmadtek.demo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeekerService {
    @Autowired
    UserRepository userRepository ;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MongoTemplate mongoTemplate;
   @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
@Autowired
    JobRepository jobRepository ;



    public Seeker createUser(SeekerRequest signUpRequest) throws Exception {
        System.out.println(signUpRequest.getEmail()+signUpRequest.getUsername());
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new Exception("Email is already in use!");
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new Exception("Username is already in use!");
        }

        Set<UserRole> roles;
        if(signUpRequest.getRoles() == null  || signUpRequest.getRoles().isEmpty() ) {
            roles = new HashSet<>();
            roles.add(UserRole.SEEKER);

        } else {
            roles = signUpRequest.getRoles().stream().map(UserRole::asRole).collect(Collectors.toSet());

        }

        System.out.println(signUpRequest.getEmail()+signUpRequest.getUsername());

        Seeker seeker = new Seeker(signUpRequest.getUsername(),
                signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getLocation(),roles);
        System.out.println(seeker.getEmail()+seeker.getUsername());

        return userRepository.save(seeker);
    }




    public Seeker editprofileSeeker(SeekerRequest userRequest) throws Exception {
        Seeker user = (Seeker) userRepository.findById(userRequest.getId()).orElseThrow(() -> new Exception("User not found"));
        System.out.println("here");
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        if(userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            user.setRoles(userRequest.getRoles().stream().map(UserRole::asRole).collect(Collectors.toSet()));
        }
        if(userRequest.getPassword()!=null){
            user.setPassword(encoder.encode(userRequest.getPassword()));
        }
        if(userRequest.getLocation()!=null){
            user.setLocation(userRequest.getLocation());
        }
        return userRepository.save(user);
    }

    public User applyForJob(String token, MultipartFile multipartFile,String idofjob) throws Exception {

        System.out.println("hey");

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String jwtToken = token.replace("Bearer ", "");
        System.out.println(jwtToken);
        String id=jwtUtils.getIdFromJwtToken(jwtToken);
        System.out.println(id);
        System.out.println(idofjob);
        Seeker seeker =(Seeker) userRepository.findById(id).orElseThrow(()->new Exception("seeker not found"));



        Job job = jobRepository.findById(idofjob).orElseThrow(()->new Exception("job not found"));


        if(seeker.getListofJob().contains(job)){
            throw new Exception("you have been applied ");
        }
        String uploadDir = "resumes/" + id ;

        System.out.println(job.getId());
        seeker.setPath_resume(uploadDir+"/"+fileName);

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        job.getListofseeker().add(seeker);
        seeker.getListofJob().add(job);


        jobRepository.save(job);

        return userRepository.save(seeker);
    }





    public HashSet<Job> FetchAllJobs(String token) throws Exception{

        String jwtToken=token.replace("Bearer","");
        String id =jwtUtils.getIdFromJwtToken(jwtToken);
        Seeker seeker = (Seeker) userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
                return seeker.getListofJob();
    }

}
