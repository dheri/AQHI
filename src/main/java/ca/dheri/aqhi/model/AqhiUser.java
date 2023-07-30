package ca.dheri.aqhi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Entity
public class AqhiUser {

    @Id
    private String id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToMany
    private Set<Location> favoriteLocations = new HashSet<>();

}