package ca.dheri.aqhi.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class AqhiUser {

    @Id
    private String id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;

    @ElementCollection
    private Map<String, Location> favoriteLocations = new HashMap<String, Location>();

}