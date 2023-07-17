package ca.dheri.aqhi.config;

import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.model.AqhiUserRepository;
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
    AqhiUserRepository aqhiUserRepository;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        logger.info(" AuthenticationEvents onSuccess called");
        Authentication authentication = success.getAuthentication();

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        aqhiUserRepository.findById(oAuth2User.getAttribute("sub")).orElseGet(() -> {
            AqhiUser u = new AqhiUser();
            u.setId(oAuth2User.getAttribute("sub"));
            u.setName(oAuth2User.getAttribute("name"));
            u.setFirstName(oAuth2User.getAttribute("given_name"));
            u.setLastName(oAuth2User.getAttribute("family_name"));
            u.setEmail(oAuth2User.getAttribute("email"));
            u = aqhiUserRepository.save(u);
            return u;
        });

//        logger.info(oAuth2User.getAttributes().toString());
//        logger.info("authentication.isAuthenticated : " + authentication.isAuthenticated());
//        logger.info(authentication.getAuthorities().stream().toList().toString());
//        logger.info(authentication.getDetails().toString());

    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        logger.error("AuthenticationEvents onFailure called");
    }
}

