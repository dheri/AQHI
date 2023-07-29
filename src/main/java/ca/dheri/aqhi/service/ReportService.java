package ca.dheri.aqhi.service;

import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReportService {
    Logger logger = LoggerFactory.getLogger(ReportService.class);
    @Autowired

    LocationService locationService;
    public String generateReport(AqhiUser user) {
        for (Location location : user.getFavoriteLocations().values()) {

            location.getId();
        }

        user.getFavoriteLocations().values()
                .stream()
                .parallel()
                .map(locationService::getLocationInfo)
                .map(d -> d.getFeatures().get(0))
                .map(p -> p.getProperties().getAqhi())
                .forEach(d -> logger.info(d.toString()));

        return "";
    }

}
