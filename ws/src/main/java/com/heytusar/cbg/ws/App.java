package com.heytusar.cbg.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This is the spring boot application for cbg game > webservice component
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
}
