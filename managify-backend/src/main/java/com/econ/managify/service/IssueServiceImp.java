package com.econ.managify.service;

import com.econ.managify.interfaces.IssueService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Issues;
import com.econ.managify.model.Project;
import com.econ.managify.model.User;
import com.econ.managify.repository.IssueRepository;
import com.econ.managify.request.IssueRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImp implements IssueService {

    private final UserService userService;
    private final IssueRepository issueRepository;
    private final ProjectService projectService;

    public IssueServiceImp(UserService userService, IssueRepository issueRepository, ProjectService projectService) {
        this.userService = userService;
        this.issueRepository = issueRepository;
        this.projectService = projectService;
    }

    @Override
    public Issues getIssueById(Long issueId) throws Exception {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new Exception("No Issue Found"));
    }


    @Override
    public List<Issues> getIssueByProjectId(Long projectId) throws Exception {
        projectService.getProjectById(projectId);
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issues createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());
        Issues issues = new Issues();
        issues.setTitle(issueRequest.getTitle());
        issues.setDescription(issueRequest.getDescription());
        issues.setStatus(issueRequest.getStatus());
        issues.setProjectID(issueRequest.getProjectId());
        issues.setPriority(issueRequest.getPriority());
        issues.setDueDate(issueRequest.getDueDate());

        issues.setProject(project);

        return issueRepository.save(issues);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
         issueRepository.deleteById(issueId);
    }

    @Override
    public Issues addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issues issue = getIssueById(issueId);
        issue.setAssigned(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issues updateStatus(Long issueId, String status) throws Exception {
        Issues issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
