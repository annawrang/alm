package se.steam.trellov2.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import se.steam.trellov2.model.Issue;
import se.steam.trellov2.repository.IssueRepository;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.model.parse.ModelParser;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.service.IssueService;
import se.steam.trellov2.service.business.Logic;

import java.util.UUID;

@Service
final class IssueServiceImp implements IssueService {

    private final IssueRepository issueRepository;
    private final TaskRepository taskRepository;
    private final Logic logic;

    private IssueServiceImp(IssueRepository issueRepository, TaskRepository taskRepository, Logic logic) {
        this.issueRepository = issueRepository;
        this.taskRepository = taskRepository;
        this.logic = logic;
    }


    /**
     * @param taskId
     * @param issue
     * @return Issue
     *
     * WARNING THIS FAILS
     *
     */
    @Override
    public Issue save(UUID taskId, Issue issue) {
        return ModelParser.fromIssueEntity(issueRepository.save(
                ModelParser.toIssueEntity(issue.assignId()).setTaskEnitity(
                        taskRepository.save(logic.failValidateTask(taskId).dropTask()))));
    }

    @Override
    public void update(Issue issue) {
        logic.validateIssue(issue.getId());
        issueRepository.save(ModelParser.toIssueEntity(issue));
    }

    @Override
    public void delete(UUID issueId) {
        issueRepository.save(logic.validateIssue(issueId).deactivate());
    }

    @Override
    public Page<Issue> getPage(UUID teamId, PagingInput pagingInput) {
        return issueRepository.findByTaskEntityTeamEntityAndActive(logic.validateTeam(teamId), true, PageRequest.of(pagingInput.getPage(), pagingInput.getSize()))
                .map(ModelParser::fromIssueEntity);
    }

}