package ca.dheri.AQHI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity

public class Location {
    @GeneratedValue
    @Id
    private  int id;
    private String city;

}
