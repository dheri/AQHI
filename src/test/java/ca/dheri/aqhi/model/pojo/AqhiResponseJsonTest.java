package ca.dheri.aqhi.model.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@JsonTest
class AqhiResponseJsonTest {
    @Autowired
    private JacksonTester<AqhiResponse> json;

    @Test
    public void testSerialization() throws Exception {

        var aqhiResponse = new AqhiResponse();
        aqhiResponse.setType("FeatureCollection");
        aqhiResponse.setFeatures(new ArrayList<>());
        aqhiResponse.setNumberMatched(6);
        aqhiResponse.setNumberReturned(6);
        aqhiResponse.setTimeStamp(new Date());

        JsonContent<AqhiResponse> result = json.write(aqhiResponse);
        System.out.println(json.write(aqhiResponse));

        assertThat(result).hasJsonPathStringValue("$.type");
        assertThat(result).extractingJsonPathStringValue("$.type").isEqualTo("FeatureCollection");


    }


}