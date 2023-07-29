package ca.dheri.aqhi.controller;


import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.service.LocationService;
import ca.dheri.aqhi.service.ReportService;
import ca.dheri.aqhi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;


    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {

        AqhiUser aqhiUser = userService.getUser(principal.getAttribute("sub"));
        String report = reportService.generateReport(aqhiUser);
        model.addAttribute("user", aqhiUser);
        model.addAttribute("report", report);
        return "index";
    }

}