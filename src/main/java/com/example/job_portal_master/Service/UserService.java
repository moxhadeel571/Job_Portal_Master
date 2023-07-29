package com.example.job_portal_master.Service;

import com.example.job_portal_master.Entity.CustomUser;
import com.example.job_portal_master.Entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    CustomUser saveUser(CustomUser customUser, Role role);

    public void removeSessionMessage();


    String encodePassword(String password);

    // Fetch user by email
    CustomUser getUserByEmail(String email);


}