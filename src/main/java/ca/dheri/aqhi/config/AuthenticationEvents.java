package ca.dheri.aqhi.config;

import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.model.AqhiUserRepository;
import ca.dheri.aqhi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {
    Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);

    @Autowired
    UserService userService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        logger.info(" AuthenticationEvents onSuccess called");
        Authentication authentication = success.getAuthentication();

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        userService.save(oAuth2User);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        logger.error("AuthenticationEvents onFailure called");
    }
}

