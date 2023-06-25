package ca.dheri.AQHI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class Location {
    @Id
    private  String id;
    private String name;
    private String city;

}
