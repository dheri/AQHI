package ca.dheri.aqhi.service;

import ca.dheri.aqhi.model.Location;
import ca.dheri.aqhi.model.pojo.AqhiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {
    Logger logger = LoggerFactory.getLogger(LocationService.class);
    private final String AQH_FORECAST_BASE_URL = "https://api.weather.gc.ca/collections/aqhi-forecasts-realtime/items";

    public AqhiResponse getLocationInfo(String locationID) throws JsonProcessingException {
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

    public AqhiResponse getLocationInfo(Location location) {
        try {
            return getLocationInfo(location.getId());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;

    }
}
