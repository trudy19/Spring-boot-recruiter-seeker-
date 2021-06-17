package com.crudmadtek.demo.repositories;



import com.crudmadtek.demo.models.User;
import com.mongodb.client.model.UpdateOneModel;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>  {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    Optional<User> findByConfirmationToken(String token);
    Optional<User> findByUsername(String user);
    GeoResults<User> findByLocationNear(Point location, Distance distance);
    @Query("{'$specialite.$id.$oid': '?2'}")

    GeoResults<User> findByLocationNearAndBySpecialite(Point location, Distance distance, String specialite);




    boolean existsByEmail(String email);
    boolean existsByUsername(String username);


}



