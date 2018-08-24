package se.steam.trellov2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.steam.trellov2.repository.model.IssueEntity;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.repository.model.TeamEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, UUID> {

    Page<IssueEntity> findByTaskEntityTeamEntityAndActive(TeamEntity teamEntity, boolean active, Pageable pageable);

    List<IssueEntity> findByTaskEntity(TaskEntity taskEntity);

}