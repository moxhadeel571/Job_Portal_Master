package com.example.job_portal_master.Controller;

import com.example.job_portal_master.Entity.Role;
import com.example.job_portal_master.Entity.CustomUser;
import com.example.job_portal_master.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;




@Controller
//@RequestMapping("/common")
public class UserController {


    @Autowired
    private  AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    @Autowired
    public UserController( PasswordEncoder passwordEncoder, UserServiceImpl userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/sign_in")
    public String processLogin(
                               Model model) {

        return "login"; // Redirect to the home page after successful login
    }
    @PostMapping("/authenticate")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {
        try {
            // Create an authentication token with the provided username and password
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(token);

            // Redirect to the dashboard page upon successful authentication
            return "redirect:/home"; // Assuming "dashboard" maps to the dashboard.html page

        } catch (AuthenticationException ex) {
            // If authentication fails, redirect back to the login page with an error message
            redirectAttributes.addAttribute("error", "Invalid email or password");
            return "redirect:/sign_in";
        }
    }



    @GetMapping("/registration")
        public String registrationForm (Model model, @ModelAttribute("user") CustomUser
        customUser, @RequestParam(name = "registrationType", required = false) String registrationType){
            model.addAttribute("user", new CustomUser());
            model.addAttribute("registrationType", registrationType);
            return "Registration";
        }

        @PostMapping("/register")
        public String registerUser (@ModelAttribute("user") CustomUser customUser, @RequestParam String registrationType)
        {
            customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));

            // Set user role based on the registration type
            Role role = (registrationType.equals("RECRUITER")) ? Role.RECRUITER : Role.NORMAL_USER;

            // Add the user role to the user entity
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            customUser.setRoles(new ArrayList<>(roles)); // Convert the Set to a List and set it in the user object


            // Save the user in the database
            userService.saveUser(customUser, role);

            // Print the values for debugging purposes
            System.out.println("Selected Role: " + role);
            System.out.println("CustomUser with Role: " + customUser.getRoles());
            System.out.println("Encoded Password: " + userService.encodePassword(customUser.getPassword()));
            System.out.println("Registration Type: " + registrationType);
            System.out.println("CustomUser Details: " + customUser);

            return "redirect:/sign_in"; // Redirect to the login page after successful registration

        }

        @GetMapping("/sign_out")
        public String signOut (HttpServletRequest request, HttpServletResponse response){
            SecurityContextHolder.clearContext();
            return "redirect:/sign_in";
        }

    }
