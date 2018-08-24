package se.steam.trellov2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.steam.trellov2.model.status.TaskStatus;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.repository.model.TeamEntity;
import se.steam.trellov2.repository.model.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByUserEntity(UserEntity userEntity);

    @Query("select t from Tasks t join t.teamEntity e where e = :teamEntity and " +
            "t.active = :active and " +
            "t.text like %:text% and " +
            "(:startDate is null or t.date >= :startDate) and " +
            "(:endDate is null or t.date <= :endDate) and " +
            "(:status is null or t.status = :status)")
    Page<TaskEntity> findByTeam(TeamEntity teamEntity,
                                boolean active,
                                String text,
                                LocalDate startDate,
                                LocalDate endDate,
                                TaskStatus status,
                                Pageable pageable);

    List<TaskEntity> findByTeamEntityAndActive(TeamEntity teamEntity, boolean active);

}