package ca.dheri.AQHI.model;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface AqhiUserRepository extends Repository<AqhiUser, Long> {
    List<AqhiUser> findByFirstName(String firstName);
    Optional<AqhiUser> findById(String id);
    AqhiUser save(AqhiUser entity);
}
