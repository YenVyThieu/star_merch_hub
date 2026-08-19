package Group.com.starmerch.Artifact.star_merch_hub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Group.com.starmerch.Artifact.star_merch_hub.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
