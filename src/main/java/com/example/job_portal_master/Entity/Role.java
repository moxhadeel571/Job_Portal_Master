package com.example.job_portal_master.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
public enum Role {
    NORMAL_USER("ROLE_NORMAL_USER"),
    RECRUITER("ROLE_RECRUITER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }



    public String getAuthority() {
        return authority;
    }
}
