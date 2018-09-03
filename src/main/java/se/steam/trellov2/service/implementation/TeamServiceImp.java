package se.steam.trellov2.service.implementation;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.model.User;
import se.steam.trellov2.repository.IssueRepository;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.TeamRepository;
import se.steam.trellov2.repository.UserRepository;
import se.steam.trellov2.repository.model.*;
import se.steam.trellov2.repository.model.parse.ModelParser;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.business.Logic;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static se.steam.trellov2.repository.model.parse.ModelParser.*;

@Service
final class TeamServiceImp implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;
    private final TaskRepository taskRepository;
    private final Logic logic;

    private TeamServiceImp(TeamRepository teamRepository, UserRepository userRepository,
                           IssueRepository issueRepository, TaskRepository taskRepository, Logic logic) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.taskRepository = taskRepository;
        this.logic = logic;
    }

    @Override
    public Team save(Team team) {
        return fromTeamEntity(teamRepository.save(toTeamEntity(team.assignId())));
    }

    @Override
    public Team get(UUID entityId) {
        return fromTeamEntity(logic.validateTeam(entityId));
    }

    @Override
    public void update(Team entity) {
        logic.validateTeam(entity.getId());
        teamRepository.save(toTeamEntity(entity));
    }

    @Override
    public void remove(UUID teamId) {
        TeamEntity teamEntity = logic.validateTeam(teamId);

        userRepository.saveAll(userRepository.findByTeamEntity(teamEntity).stream()
                .map(UserEntity::leaveTeam)
                .collect(Collectors.toList()));

        List<TaskEntity> taskEntities = taskRepository.findByTeamEntityAndActive(teamEntity, true);

        taskEntities.forEach((t) ->
                issueRepository.saveAll(issueRepository.findByTaskEntity(t).stream()
                        .map(IssueEntity::deactivate)
                        .collect(Collectors.toList()))
        );

        taskRepository.saveAll(taskEntities.stream()
                .map(TaskEntity::deactivate)
                .collect(Collectors.toList()));

        teamRepository.save(logic.validateTeam(teamId).deactivate());
    }

    @Override
    public Pair<Team, User> addUserToTeam(UUID teamId, UUID userId) {
        UserEntity s = userRepository.save(logic.checkUserTeamAvailability(logic.validateUser(userId))
                .setTeamEntity(logic.checkTeamMaxCap(logic.validateTeam(teamId))));
        return Pair.of(fromTeamEntity(s.getTeamEntity()), fromUserEntity(s));
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll()
                .stream()
                .filter(AbstractEntity::isActive)
                .map(ModelParser::fromTeamEntity)
                .collect(Collectors.toList());
    }

}