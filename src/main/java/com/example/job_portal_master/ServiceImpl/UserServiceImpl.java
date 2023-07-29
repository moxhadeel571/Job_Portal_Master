package com.example.job_portal_master.ServiceImpl;

import com.example.job_portal_master.Entity.CustomUser;
import com.example.job_portal_master.Entity.Role;
import com.example.job_portal_master.Repository.UserRepository;
import com.example.job_portal_master.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CustomUser saveUser(CustomUser customUser, Role role) {
        String password = passwordEncoder.encode(customUser.getPassword());
        customUser.setPassword(password);

        // Set user role
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        customUser.setRoles(new ArrayList<>(roles)); // Convert the Set to a List and set it in the user object

        return userRepository.save(customUser);
    }

    @Override
    public void removeSessionMessage() {
        // Implement any required logic here
    }

    @Override
    public String encodePassword(String password) {
        // Use the provided PasswordEncoder to encode the password
        return passwordEncoder.encode(password);
    }

    @Override
    public CustomUser getUserByEmail(String email) {
        return userRepository.findByUsername(email);
    }





}
