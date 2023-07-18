package ca.dheri.aqhi.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    Logger logger = LoggerFactory.getLogger(AppController.class);



    @GetMapping("/")
    public String home() {
        return "index";
    }

}