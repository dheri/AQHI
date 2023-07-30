package ca.dheri.aqhi.service;

import ca.dheri.aqhi.model.AqhiUser;
import ca.dheri.aqhi.model.Location;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    Logger logger = LoggerFactory.getLogger(ReportService.class);
    @Autowired

    LocationService locationService;

    public TimeSeriesCollection generateReport(AqhiUser user) {
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

        user.getFavoriteLocations().values()
                .stream().parallel()
                .peek(l -> {
                    timeSeriesCollection.addSeries(locationService.getLocationAqhiTimeSeries(l));
                }).forEach(d -> logger.info(d.toString()));

        return timeSeriesCollection;
    }

}
