package com.econ.managify.controller;

import com.econ.managify.DTO.IssueDTO;
import com.econ.managify.interfaces.IssueService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Issues;
import com.econ.managify.model.User;
import com.econ.managify.request.IssueRequest;
import com.econ.managify.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueService issueService;
    private final UserService userService;

    public IssueController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<Issues> getIssueById(@PathVariable Long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Issues> getIssueByProjectId(@PathVariable Long ProjectId) throws Exception{
        return ResponseEntity.ok((Issues) issueService.getIssueByProjectId(ProjectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue, @RequestHeader("Authorization") String token) throws Exception{
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());
            Issues createdIssue = issueService.createIssue(issue, tokenUser);
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setDescription(createdIssue.getDescription());
            issueDTO.setDueDate(createdIssue.getDueDate());
            issueDTO.setId(createdIssue.getId());
            issueDTO.setPriority(createdIssue.getPriority());
            issueDTO.setProjectID(createdIssue.getProjectID());
            issueDTO.setProject(createdIssue.getProject());
            issueDTO.setStatus(createdIssue.getStatus());
            issueDTO.setTags(createdIssue.getTags());
            issueDTO.setTitle(createdIssue.getTitle());
            issueDTO.setAssignee(createdIssue.getAssigned());
            return  ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteIssue(@PathVariable Long issueId, @RequestHeader("Authorization") String token) throws  Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());
        ApiResponse res = new ApiResponse("Deleted");
        res.setMessage("Issue deleted");
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issues> addUserToIssue(@PathVariable Long issueId, @PathVariable Long userId) throws Exception{
        Issues issues = issueService.addUserToIssue(issueId,userId);
        return ResponseEntity.ok(issues);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issues> updateIssueStatus(@PathVariable String status, @PathVariable Long issueId) throws Exception{
        Issues issues = issueService.updateStatus(issueId,status);
        return ResponseEntity.ok(issues);
    }

}
