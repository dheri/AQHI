package ca.dheri.aqhi.service;


import ca.dheri.aqhi.controller.ApiController;
import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.model.AqhiUserRepository;
import ca.dheri.aqhi.model.Location;
import ca.dheri.aqhi.model.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    AqhiUserRepository aqhiUserRepository;
    @Autowired
    LocationRepository locationRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public AqhiUser save(OAuth2User oAuth2User) {

        AqhiUser user = getUserOptional(oAuth2User.getAttribute("sub")).orElseGet(() -> {
            AqhiUser u = new AqhiUser();
            u.setId(oAuth2User.getAttribute("sub"));
            u.setName(oAuth2User.getAttribute("name"));
            u.setFirstName(oAuth2User.getAttribute("given_name"));
            u.setLastName(oAuth2User.getAttribute("family_name"));
            u.setEmail(oAuth2User.getAttribute("email"));
            u = aqhiUserRepository.save(u);
            return u;
        });

        return user;
    }

    public Optional<AqhiUser> getUserOptional(String id) {
        Optional<AqhiUser> user = aqhiUserRepository.findById(id);
        return user;
    }

    public AqhiUser getUser(String id) {
        Optional<AqhiUser> user = aqhiUserRepository.findById(id);
        return user.orElseThrow();
    }

    public List<Location> getFavoriteLocations(String userId) {
        AqhiUser user = getUser(userId);
        return List.copyOf(user.getFavoriteLocations().stream().toList());
    }

    public AqhiUser addFavoriteLocation(String userId, String locationId) {
        AqhiUser user = getUser(userId);
        logger.info("addFavoriteLocation user: ", user.toString(), ", location: ", locationId);

        user.getFavoriteLocations().add(locationRepository.findById(locationId).orElseThrow());
        return aqhiUserRepository.save(user);
    }

    public AqhiUser deleteFavoriteLocation(String userId, String locationId) {
        AqhiUser user = getUser(userId);
        boolean isRemoved = user.getFavoriteLocations().removeIf(l -> l.getId().equals(locationId));
        logger.info("removed Location: ", locationId, " : ", isRemoved);

        return aqhiUserRepository.save(user);
    }


}