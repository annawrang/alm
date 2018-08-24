package se.steam.trellov2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.steam.trellov2.repository.model.TeamEntity;
import se.steam.trellov2.repository.model.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> findByTeamEntity(TeamEntity teamEntity);

    Page<UserEntity> findByFirstNameContainingAndLastNameContainingAndUsernameContainingAndActive(String firstName, String lastName, String username, boolean active, Pageable pageable);

}