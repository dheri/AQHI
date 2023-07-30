package ca.dheri.aqhi.service;

import ca.dheri.aqhi.model.pojo.AqhiResponse;
import ca.dheri.aqhi.model.pojo.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.data.time.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    @Test
    public void t1() throws IOException {
        String respJson = new String(getClass().getClassLoader().getResourceAsStream("t.json").readAllBytes());
        System.out.println(respJson.length());
//        System.out.println(respJson.substring(35));
        ObjectMapper objectMapper = new ObjectMapper();
        AqhiResponse aqhiResponse = objectMapper.readValue(respJson, AqhiResponse.class);
        System.out.println(aqhiResponse.getFeatures().size());

        TimeSeries timeSeries = new TimeSeries("FDQBX");
        aqhiResponse.getFeatures().stream()
                .map(f -> f.getProperties())
                .peek(p -> System.out.println("Mapped value: " + p.getAqhi()))
                .peek(p ->
                        timeSeries.addOrUpdate(
                                new TimeSeriesDataItem(new Hour(p.getForecast_datetime()), p.getAqhi())
                        )
                ).collect(Collectors.toList());
        ;
//        TimePeriodValue timePeriodValue = new TimePeriodValue(new Hour(prop.getForecast_datetime()), prop.getAqhi());
        System.out.println("getDescription " + timeSeries.getDescription());
        System.out.println("getItemCount " + timeSeries.getItemCount());
        System.out.println("getMaxStartIndex " + timeSeries.getMaxY());
        System.out.println("getMaxEndIndex " + timeSeries.getMinY());
        System.out.println("findValueRange " + timeSeries.findValueRange());
        System.out.println("findValueRange getLowerBound " + timeSeries.findValueRange().getLowerBound());
        System.out.println("findValueRange getUpperBound " + timeSeries.findValueRange().getUpperBound());


    }
}