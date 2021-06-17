package com.crudmadtek.demo.repositories;


import com.crudmadtek.demo.models.Seeker;
import com.crudmadtek.demo.models.User;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeekerRepository extends MongoRepository<Seeker, String> {


    Optional<User> findByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    Optional<User> findByConfirmationToken(String token);
    Optional<User> findByUsername(String user);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
