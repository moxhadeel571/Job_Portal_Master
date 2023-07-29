package com.example.job_portal_master.Handler;

import com.example.job_portal_master.Entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
@Component
public class YourCustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains(Role.RECRUITER.getAuthority())) {
            response.sendRedirect("/profile"); // Redirect recruiter to recruitordashboard
        } else if (roles.contains(Role.NORMAL_USER.getAuthority())) {
            response.sendRedirect("/joblisting"); // Redirect normal user to /joblisting
        } else {
            response.sendRedirect("/home"); // Redirect to /signin for any other roles (if needed)
        }
    }
}
