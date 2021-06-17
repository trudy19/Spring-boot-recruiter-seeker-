package com.crudmadtek.demo.services;

import com.crudmadtek.demo.models.Job;
import com.crudmadtek.demo.models.Jobindeed;
import com.crudmadtek.demo.repositories.IndeedRepository;
import com.crudmadtek.demo.repositories.JobRepository;
import com.crudmadtek.demo.repositories.RecruiterRepository;
import com.crudmadtek.demo.repositories.UserRepository;
import com.crudmadtek.demo.security.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j

public class JobService {
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
    @Autowired
    IndeedRepository indeedRepository;

    public  List<Job> FetchAllJobs() throws Exception{

        List<Job> results= jobRepository.findAll();
        return results;
    }


    public   List<Jobindeed>FetchAllJobsIndeed() throws Exception{

        List<Jobindeed> results= indeedRepository.findAll();
        return results;
    }
}
