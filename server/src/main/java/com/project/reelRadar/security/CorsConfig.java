package com.project.reelRadar.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS (Cross-Origin Resource Sharing) mappings for the application.
     * This allows the specified origins to access the resources of the server
     * and defines the allowed HTTP methods (verbs).
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://miguelcardoso19.github.io/ReelRadar/")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}