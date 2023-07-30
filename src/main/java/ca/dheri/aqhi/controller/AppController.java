package ca.dheri.aqhi.controller;


import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.model.Location;
import ca.dheri.aqhi.service.LocationService;
import ca.dheri.aqhi.service.ReportService;
import ca.dheri.aqhi.service.UserService;
import org.jfree.data.time.TimeSeriesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
public class AppController {
    Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    LocationService locationService;


    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {

        AqhiUser aqhiUser = userService.getUser(principal.getAttribute("sub"));
        TimeSeriesCollection report = reportService.generateReport(aqhiUser);
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("user", aqhiUser);
        model.addAttribute("locations", locations);
        model.addAttribute("report", report);
        return "index";
    }

    @GetMapping("/")
    public ResponseEntity<Void> root() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/home").build().toUri());
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.FOUND);

    }
}