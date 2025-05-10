package org.bbyoda.secondservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondServiceController {

    @GetMapping("/second/hello")
    public String hello() {
        return "Hello from second service!";
    }
}
