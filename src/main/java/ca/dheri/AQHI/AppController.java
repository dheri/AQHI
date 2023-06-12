package ca.dheri.AQHI;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AppController {
    Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        logger.info(principal.getAttributes().toString());

        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

}