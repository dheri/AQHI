package ca.dheri.aqhi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Location {
    @Id
    private String id;
    private String name;
    private String city;

}
