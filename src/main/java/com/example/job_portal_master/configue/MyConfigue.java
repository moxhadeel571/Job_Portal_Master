package com.example.job_portal_master.configue;

import com.example.job_portal_master.Authentication.CustomAuthenticationEntryPoint;
import com.example.job_portal_master.Handler.YourCustomAuthenticationSuccessHandler;
import com.example.job_portal_master.Handler.YourCustomAuthenticationFailureHandler;
import com.example.job_portal_master.ServiceImpl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan("com.example.job_portal_master")
public class MyConfigue extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsService customUserDetailsService;
	private final YourCustomAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	public MyConfigue(@Lazy CustomUserDetailsService customUserDetailsService, YourCustomAuthenticationSuccessHandler authenticationSuccessHandler) {
		this.customUserDetailsService = customUserDetailsService;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/home", "/sign_in", "/registration", "/register", "/authenticate").permitAll()
				.antMatchers("/Displaycompany", "/viewjob/{id}", "/upload", "/companylisting","/joblisting").hasRole("NORMAL_USER")
				.antMatchers("/Displaycandidates", "/send_email", "/saveresume", "/resumedashboard", "/send_email_resume", "/savecompany", "/download_static/{candidateId}", "/download/{staticCandidateId}", "/profile").hasRole("RECRUITER")
				.and()
				.formLogin()
				.loginPage("/sign_in") // Set the login page URL
				.loginProcessingUrl("/authenticate")
				.successHandler(authenticationSuccessHandler) // Set the custom success handler
				.failureHandler(authenticationFailureHandler())
				.permitAll()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint())
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/sign_in?logout") // Corrected the logout URL
				.permitAll()
				.and()
				.csrf().disable(); // Disable CSRF protection
	}

	@Bean
	public YourCustomAuthenticationFailureHandler authenticationFailureHandler() {
		return new YourCustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
