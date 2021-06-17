package com.crudmadtek.demo.services;

import com.crudmadtek.demo.models.User;
import com.crudmadtek.demo.payload.request.LoginRequest;
import com.crudmadtek.demo.repositories.UserRepository;

import com.crudmadtek.demo.security.jwt.JwtUtils;
import com.crudmadtek.demo.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    public User authenticateUser(LoginRequest loginRequest) throws Exception{
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        System.out.println("1");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("2");

        System.out.println(loginRequest.getUsername());


        String jwt = jwtUtils.generateJwtToken(authentication);

        System.out.println("3");

        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        System.out.println("4");

        User u = userRepository.findById(userDetails.getId()).orElseThrow(() -> new Exception("User not found"));
        u.setToken(jwt);

        userRepository.save(u);
        return u;
    }



    public String deleteUser(String userId) {
        userRepository.deleteById(userId);
        return "done";
    }






}
