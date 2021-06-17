package com.crudmadtek.demo.services;


import com.crudmadtek.demo.models.*;
import com.crudmadtek.demo.payload.request.JobRequest;
import com.crudmadtek.demo.payload.request.RecruiterRequest;
import com.crudmadtek.demo.payload.response.SeekerResponse;
import com.crudmadtek.demo.repositories.JobRepository;
import com.crudmadtek.demo.repositories.RecruiterRepository;
import com.crudmadtek.demo.repositories.UserRepository;
import com.crudmadtek.demo.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class RecruiterService  {
    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    UserRepository userRepository;

    @Autowired
    RecruiterRepository recruiterRepository ;



    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JobRepository jobRepository;


    public  Recruiter createUser(RecruiterRequest signUpRequest) throws Exception {
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
            roles.add(UserRole.RECRUITER);

        } else {
            roles = signUpRequest.getRoles().stream().map(str -> UserRole.asRole(str)).collect(Collectors.toSet());

        }

        System.out.println(signUpRequest.getEmail()+signUpRequest.getUsername());

        Recruiter recruiter = new Recruiter(signUpRequest.getUsername(),
                signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getLocation(),roles);
        System.out.println(recruiter.getEmail()+recruiter.getUsername());

//        String activationToken = UUID.randomUUID().toString() + encoder.encode(recruiter.getUsername());
//       activationToken = activationToken.replace("/","");
//        recruiter.setConfirmationToken(activationToken);
//        String activationUrl = "http://localhost:3000/" + "auth/activate/" + activationToken;
//        emailService.sendConfirmationMail(recruiter,activationUrl,"confirmation.html","Email Verification");
//

        return userRepository.save(recruiter);
    }

    public User editprofileRecruiter(RecruiterRequest userRequest) throws Exception {
        Recruiter user = (Recruiter) userRepository.findById(userRequest.getId()).orElseThrow(() -> new Exception("User not found"));
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



    public Recruiter POSTJob(JobRequest jobRequest,String token ) throws Exception {

        String jwtToken = token.replace("Bearer ", "");
        String idofrecuiter=jwtUtils.getIdFromJwtToken(jwtToken);

        Recruiter recruiter = (Recruiter) recruiterRepository.findById(idofrecuiter).orElseThrow(() -> new Exception("User not found"));

System.out.println(recruiter.getEmail());

        Job job=new Job(jobRequest.getTitle(),jobRequest.getDescription(),jobRequest.getLocation(),jobRequest.getType(),jobRequest.getSeniority_level(),jobRequest.getSkill_required(),recruiter);

        System.out.println(job.getRecruiter().getEmail());

        jobRepository.save(job);

        recruiter.getListofjob().add(job);

        return userRepository.save(recruiter);
    }


    public String deleteJob(String jobId) throws Exception {
        Job job=  jobRepository.findById(jobId).orElseThrow(() -> new Exception("job not found"));
        Recruiter recruiter = (Recruiter) recruiterRepository.findById(job.getRecruiter().getId()).orElseThrow(() -> new Exception("User not found"));
        recruiter.getListofjob().removeIf(s->s.getId().equals(jobId));
        recruiterRepository.save(recruiter);
        jobRepository.deleteById(jobId);
        return "job Deleted";
    }




    public HashSet<Job> FetchAllJobs(String token) throws Exception{

        String jwtToken = token.replace("Bearer ", "");
        String idofrecuiter=jwtUtils.getIdFromJwtToken(jwtToken);
        System.out.println("hereeeeeeeeee"+idofrecuiter);
        Recruiter recruiter = (Recruiter) recruiterRepository.findById(idofrecuiter).orElseThrow(() -> new Exception("User not found"));


        return recruiter.getListofjob() ;
    }



    public HashSet<SeekerResponse> FetchAllSeeker(String token,String jobId) throws Exception{

        String jwtToken = token.replace("Bearer ", "");
        String idofrecuiter=jwtUtils.getIdFromJwtToken(jwtToken);
        System.out.println("hereeeeeeeeee"+idofrecuiter);
        Recruiter recruiter = (Recruiter) recruiterRepository.findById(idofrecuiter).orElseThrow(() -> new Exception("User not found"));
        Job job =Job.findingjob(jobId,recruiter.getListofjob());
        return job.getListofseeker().stream().map(str -> {
                   return new SeekerResponse(str.getId(),str.getUsername(),str.getEmail(),str.getPath_resume());
        }).collect(Collectors.toCollection(HashSet::new));
//    //    job.getListofseeker()
//        System.out.println(seekerlist.isEmpty()+"+"+seekerlist.size()+job.);

    }


    public Job editJob(JobRequest jobRequest,String token) throws Exception {


        String jwtToken = token.replace("Bearer ", "");
        String idofrecuiter=jwtUtils.getIdFromJwtToken(jwtToken);
        System.out.println("hereeeeeeeeee"+idofrecuiter);
        Recruiter recruiter = (Recruiter) recruiterRepository.findById(idofrecuiter).orElseThrow(() -> new Exception("User not found"));

        Job job=jobRepository.findById(jobRequest.getId()).orElseThrow(()->new Exception("job not found "));
        //job.setTitle(jobRequest.getTitle());


        System.out.println(job.getRecruiter().getId());
        System.out.println(idofrecuiter);

        if(idofrecuiter.equals(job.getRecruiter().getId())) {


            if (jobRequest.getTitle() != null) {
                job.setTitle(jobRequest.getTitle());
            }
            if (jobRequest.getLocation() != null) {
                job.setLocation(jobRequest.getLocation());
            }
            if (jobRequest.getType() != null) {
                job.setType(jobRequest.getType());
            }
            if (jobRequest.getSkill_required() != null) {
                job.setSkill_required(jobRequest.getSkill_required());
            }
            if (jobRequest.getSeniority_level() != null) {
                job.setSeniority_level(jobRequest.getSeniority_level());
            }
        }
        else throw  new Exception("id not found in list job of recruiter");


        return jobRepository.save(job);

}


    public Resource loadFileAsResource( String uploadDir) throws IOException {
        try {
            Path uploadPath = Paths.get(uploadDir);

            Path filePath = uploadPath.normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found " );
            }
        } catch (IOException ex) {
            throw new IOException("File not found " +  ex);
        }
    }




}


