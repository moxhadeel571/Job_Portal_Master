package com.example.job_portal_master.Repository;

import com.example.job_portal_master.Entity.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends MongoRepository<CustomUser, String> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    @Query("{'email': ?0}")
    CustomUser findByUsername(String username);
}