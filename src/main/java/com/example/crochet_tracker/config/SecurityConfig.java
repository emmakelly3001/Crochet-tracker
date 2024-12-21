package com.example.crochet_tracker.config;

//Deals with successful authenticaton
import com.example.crochet_tracker.security.CustomAuthenticationSuccessHandler;
//
import org.springframework.context.annotation.Bean;
//Marks this as a config class in spring, allows spring to manage the beans inside it
import org.springframework.context.annotation.Configuration;
//
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//Enables spring security in the project
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//Encrypts the passwords using BCrypt hashing algorithm
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
import org.springframework.security.crypto.password.PasswordEncoder;
//Configures security filters, handles http requests
import org.springframework.security.web.SecurityFilterChain;

//defining the class
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //injecting the CustomAuthenticationSuccessHandler
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    //defining security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //disable CSRF protection
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //allow these end points without authentication
                        .requestMatchers("/html/login.html", "/html/register.html", "/css/**", "/js/**").permitAll()
                        //allow registration API endpoint without authentication
                        .requestMatchers("/api/auth/register").permitAll()
                        //Only allow users with 'ADMIN' role to access these endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        //Require authentication fot all other requests
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        //specify custom login page
                        .loginPage("/html/login.html")
                        //the URL where the form will be submitted
                        .loginProcessingUrl("/api/auth/login")
                        //Direct to the home page on successful login
                        .defaultSuccessUrl("/html/home.html", true)
                        //Redirect back to the login page with an error param on failure
                        .failureUrl("/html/login.html?error=true")
                        //use the custom success handler
                        .successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> logout
                        //URL to log out
                        .logoutUrl("/logout")
                        //redirect to the home page on logout
                        .logoutSuccessUrl("/html/login.html"))
                //redirect to error page if access is denied
                .exceptionHandling(exception -> exception.accessDeniedPage("/error"));
        //return to the HTTP security filter chain
        return http.build();
    }

    //returns a BCrypt password encoder for password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
