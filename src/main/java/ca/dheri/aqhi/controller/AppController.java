package ca.dheri.aqhi.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Controller
public class AppController {
    Logger logger = LoggerFactory.getLogger(AppController.class);


    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        logger.info(principal.getAttributes().toString());
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    @GetMapping("/")
    public String home() {
        return "index";
    }


    @GetMapping("/location")
    public String fetchLocation() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://api.weather.gc.ca/collections/aqhi-forecasts-realtime/items?location_id=FDQBX";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);
        logger.info(response.getStatusCode().toString());
        logger.info(response.toString());
        logger.info(response.getBody().toString());
        return "index";
    }

}