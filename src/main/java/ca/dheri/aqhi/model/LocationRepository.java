package ca.dheri.aqhi.model;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends Repository<Location, String> {
    Optional<Location> findById(String id);
    Location save(Location entity);
}
