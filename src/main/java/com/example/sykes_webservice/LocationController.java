package com.example.sykes_webservice;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @PostConstruct
    public void init(){
        System.out.println("Ich wurde erzeugt");
    }

    @GetMapping("/location")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "Leoben") String name) {
        return null;
    }
}


