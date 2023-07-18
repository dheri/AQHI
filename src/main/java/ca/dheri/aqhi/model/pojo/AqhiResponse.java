package ca.dheri.aqhi.model.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AqhiResponse {
    private String type;
    private List<Feature> features;
    private int numberMatched;
    private int numberReturned;
    private List<Link> links;
    private Date timeStamp;
}
