package ca.dheri.aqhi.model.pojo;

import lombok.Data;

@Data
public class Feature {
    private String type;
    private String id;
    private Geometry geometry;
    private Properties properties;
}
