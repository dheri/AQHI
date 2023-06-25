package ca.dheri.AQHI.config;

import ca.dheri.AQHI.controller.AppController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {
    Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        logger.info(" AuthenticationEvents onSuccess called");
        Authentication authentication = success.getAuthentication();

        logger.info(( (OAuth2User) authentication.getPrincipal()).getAttributes().toString());
        logger.info( "authentication.isAuthenticated : " +   authentication.isAuthenticated());
        logger.info(  authentication.getAuthorities().stream().toList().toString());

    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        logger.info(" AuthenticationEvents onFailure called");
    }
}

