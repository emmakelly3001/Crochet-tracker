package com.example.crochet_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration// this class provides config for springs application context
public class WebConfig implements WebMvcConfigurer {

    @Override
    //cross-origin resource sharing(CORS) for the app
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//CORS for all end points
                .allowedOrigins("http://localhost:3000") // Specifies which frontend URL us allowed to make requests to the back end
                .allowedMethods("GET", "POST", "PUT", "DELETE")//specifies which HTTP methods are allowed from the front end
                .allowedHeaders("*")//allows all http headers to be sent in requests
                .allowCredentials(true);//allows all sending of credentials (cookies, authorisation headers etc.)
    }
}
