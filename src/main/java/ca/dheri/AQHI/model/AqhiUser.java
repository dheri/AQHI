package ca.dheri.AQHI.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AqhiUser {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;

    @ElementCollection
    private List<Location> favoriteLocations  = new ArrayList<Location>();

}