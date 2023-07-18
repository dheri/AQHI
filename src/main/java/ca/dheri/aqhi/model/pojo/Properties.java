package ca.dheri.aqhi.model.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Properties {
    private String id;
    private String aqhi_type;
    private String forecast_type;
    private String location_name_en;
    private String location_name_fr;
    private String location_id;
    private Date publication_datetime;
    private String forecast_datetime_text_en;
    private String forecast_datetime_text_fr;
    private Date forecast_datetime;
    private int aqhi;
}
