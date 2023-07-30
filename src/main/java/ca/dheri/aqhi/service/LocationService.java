package ca.dheri.aqhi.service;

import ca.dheri.aqhi.model.Location;
import ca.dheri.aqhi.model.LocationRepository;
import ca.dheri.aqhi.model.pojo.AqhiResponse;
import ca.dheri.aqhi.model.pojo.Link;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    Logger logger = LoggerFactory.getLogger(LocationService.class);
    private final String AQH_FORECAST_BASE_URL = "https://api.weather.gc.ca/collections/aqhi-forecasts-realtime/items";

    @Autowired
    LocationRepository locationRepository;
    public AqhiResponse getLocationAqhiTimeSeries(String locationID) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String aqhiForecastUrl = AQH_FORECAST_BASE_URL + "?location_id=" + locationID;
        ResponseEntity<String> response = restTemplate.getForEntity(aqhiForecastUrl, String.class);
        logger.info(response.getStatusCode().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        AqhiResponse aqhiResponse = objectMapper.readValue(response.getBody().toString(), AqhiResponse.class);
        logger.info(aqhiResponse.getFeatures().get(0).getProperties().getLocation_name_en());
        logger.info(String.valueOf(aqhiResponse.getFeatures().get(0).getProperties().getAqhi()));
        return aqhiResponse;
    }

    public TimeSeries getLocationAqhiTimeSeries(Location location) {
        TimeSeries timeSeries = new TimeSeries(location.getName());
        AqhiResponse aqhiResponse = new AqhiResponse();
        try {
            aqhiResponse= getLocationAqhiTimeSeries(location.getId());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        aqhiResponse.getFeatures().stream()
                .map(f -> f.getProperties())
                .peek(p ->
                        timeSeries.addOrUpdate(
                                new TimeSeriesDataItem(new Hour(p.getForecast_datetime()), p.getAqhi())
                        )
                ).collect(Collectors.toList());

        return timeSeries;
    }
    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }
}
