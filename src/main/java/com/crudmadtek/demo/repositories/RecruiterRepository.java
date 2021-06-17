package com.crudmadtek.demo.repositories;


import com.crudmadtek.demo.models.User;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruiterRepository extends MongoRepository<User,String> {

    Optional<User> findById(String id);
    Optional<User> findByResetPasswordToken(String token);
    Optional<User> findByConfirmationToken(String token);
    Optional<User> findByUsername(String user);
    GeoResults<User> findByLocationNear(Point location, Distance distance);
    @Query("{'$specialite.$id.$oid': '?2'}")

    GeoResults<User> findByLocationNearAndBySpecialite(Point location, Distance distance, String specialite);




    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
