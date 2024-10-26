package com.econ.managify.controller;

import com.econ.managify.interfaces.InvitationService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Chat;
import com.econ.managify.model.Invitation;
import com.econ.managify.model.Project;
import com.econ.managify.model.User;
import com.econ.managify.request.InviteRequest;
import com.econ.managify.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final InvitationService invitationService;
    private final ProjectService projectService;
    private final UserService userService;

    // Constructor Injection
    public ProjectController(InvitationService invitationService, ProjectService projectService, UserService userService) {
        this.invitationService = invitationService;
        this.projectService = projectService;
        this.userService = userService;
    }

    // GET endpoint to fetch projects, optionally filtered by category or tag
    @GetMapping()
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // GET endpoint to fetch a project by its ID
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // POST endpoint to create a new project
    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    // PATCH endpoint to update an existing project
    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    // DELETE endpoint to delete a project
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        ApiResponse apiResponse = new ApiResponse("Project Deleted Successfully.");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // GET endpoint to search for projects by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProject(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<ApiResponse> inviteProject(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(),req.getProjectId());
        ApiResponse apiResponse = new ApiResponse("User Invitation Sent");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }

}
