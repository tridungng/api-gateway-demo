package org.bbyoda.firstservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstServiceController {

    @GetMapping("/first/hello")
    public String hello() {
        return "Hello from first service!";
    }
}
