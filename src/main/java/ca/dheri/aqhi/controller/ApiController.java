package ca.dheri.aqhi.controller;

import ca.dheri.aqhi.model.Location;
import ca.dheri.aqhi.service.LocationService;
import ca.dheri.aqhi.service.ReportService;
import ca.dheri.aqhi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {
    Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    UserService userService;
    @Autowired
    LocationService locationService;

    @PutMapping("/favoriteLocation/{locationId}")
    public ResponseEntity<Void> putFavoriteLocation(@PathVariable String locationId, @AuthenticationPrincipal OAuth2User principal) {
        logger.info("putFavoriteLocation called");
        userService.addFavoriteLocation(principal.getAttribute("sub"), locationId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/home").build().toUri());
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.FOUND);
    }

    @DeleteMapping("/favoriteLocation/{locationId}")
    public ResponseEntity deleteFavoriteLocation(@PathVariable String locationId, @AuthenticationPrincipal OAuth2User principal) {
        logger.info("deleteFavoriteLocation called");
        userService.deleteFavoriteLocation(principal.getAttribute("sub"), locationId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/home").build().toUri());
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/favoriteLocation")
    public List<Location> getFavoriteLocation(@AuthenticationPrincipal OAuth2User principal) {
        logger.info("getFavoriteLocation called");
        return userService.getFavoriteLocations(principal.getAttribute("sub"));
    }

    @GetMapping("/locations")
    public List<Location> getAllLocation() {
        logger.info("getFavoriteLocation called");
        return locationService.getAllLocations();
    }

}
