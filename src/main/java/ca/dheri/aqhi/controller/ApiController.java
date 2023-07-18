package ca.dheri.aqhi.controller;

import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.model.Location;
import ca.dheri.aqhi.model.pojo.AqhiResponse;
import ca.dheri.aqhi.service.LocationService;
import ca.dheri.aqhi.service.ReportService;
import ca.dheri.aqhi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {
    Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    LocationService locationService;
    @GetMapping("/user")
    @ResponseBody
    public AqhiUser getUserDetails(@AuthenticationPrincipal OAuth2User principal) {
        logger.info(principal.getAttributes().toString());
        principal.getAttribute("sub");
        return userService.getUser(principal.getAttribute("sub"));

//        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @PutMapping("/favoriteLocation/{locationId}")
    public String putFavoriteLocation(@PathVariable String locationId, @AuthenticationPrincipal OAuth2User principal) {
        logger.info("putFavoriteLocation called");
        logger.info(" locationId: " + locationId + " sub: " + principal.getAttribute("sub"));
        userService.addFavoriteLocation(principal.getAttribute("sub"), locationId);
        return "putLocation";
    }

    @DeleteMapping("/favoriteLocation/{locationId}")
    public String deleteFavoriteLocation(@PathVariable String locationId, @AuthenticationPrincipal OAuth2User principal) {
        userService.deleteFavoriteLocation(principal.getAttribute("sub"), locationId);
        return "deleteLocation";
    }

    @GetMapping("/favoriteLocation")
    public List<Location> getFavoriteLocation(@AuthenticationPrincipal OAuth2User principal) {
        logger.info("getFavoriteLocation called");

        return userService.getFavoriteLocations(principal.getAttribute("sub"));
    }

    @GetMapping("/report")
    public String getReport() {
        return "getReport";
    }

    @GetMapping("/location/{id}")
    public AqhiResponse getLocation(@PathVariable String id) throws JsonProcessingException {
        return locationService.getLocationInfo(id);
    }
}
