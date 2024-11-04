package com.econ.managify.service;


import com.econ.managify.interfaces.ChatService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Chat;
import com.econ.managify.model.Project;
import com.econ.managify.model.User;
import com.econ.managify.repository.ProjectRepository;
import com.econ.managify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    private static final Long projectId = 1L;
    private static final Long userId = 1L;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ChatService chatService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ProjectServiceImp projectServiceImp;

    @Test
    public void createAProject_ShouldReturnSavedProject_WhenValidDataIsProvided() throws Exception {
        // Arrange
        User user = new User();
        user.setProjectSize(0);

        Project project = new Project();
        project.setName("Test Project");
        project.setTags(Collections.singletonList("Test"));
        project.setCategory("Development");
        project.setDescription("Test Description");

        Project savedProject = new Project();
        savedProject.setId(1L);
        savedProject.setOwner(user);
        savedProject.setName("Test Project");

        Chat chat = new Chat();
        chat.setId(1L);
        chat.setProject(savedProject);

        when(projectRepository.save(Mockito.any(Project.class))).thenReturn(savedProject);
        when(chatService.createChat(Mockito.any(Chat.class))).thenReturn(chat);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Act
        Project createdProject = projectServiceImp.createProject(project, user);

        // Assert
        assertNotNull(createdProject);
        assertEquals(user, createdProject.getOwner());
        assertEquals("Test Project", createdProject.getName());
        assertEquals(chat, createdProject.getChat());

        // Verify
        verify(projectRepository, times(1)).save(any(Project.class));
        verify(chatService, times(1)).createChat(any(Chat.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void getProjectById_ShouldReturnProject_WhenProjectExists() throws Exception {

        Project project = new Project();
        project.setId(projectId);

        // Arrange
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act
        Project findProjectById = projectServiceImp.getProjectById(projectId);

        // Assert
        assertNotNull(findProjectById, "Project should not be null");
        assertEquals(projectId, findProjectById.getId(), "Project ID should match the expected value");


        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    public void deleteProject_ShouldReturnNothing_WhenProjectDeleted() throws Exception {
        // Arrange
        Project project = new Project();
        project.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act
        projectServiceImp.deleteProject(projectId, userId);


        verify(projectRepository, times(1)).deleteById(projectId);


        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Assert
        Optional<Project> deletedProject = projectRepository.findById(projectId);
        assertFalse(deletedProject.isPresent(), "Project should not be found after deletion");
    }

    @Test
    public void updateProject_ShouldReturnProject_WhenProjectUpdated() throws Exception {
        // Arrange
        Project project = new Project();
        project.setId(projectId);
        project.setName("Updating");
        project.setDescription("Updating Description");
        project.setTags(Collections.singletonList("Updating"));

        // Mock the repository to return the updated project
        when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project)); // Mock finding the project by ID

        // Act
        Project updatedProject = projectServiceImp.updateProject(project, projectId);

        // Assert
        assertNotNull(updatedProject);
        assertEquals(projectId, updatedProject.getId(), "Project ID should match the expected value");
        assertEquals("Updating", updatedProject.getName(), "Project name should match the updated value");
        assertEquals("Updating Description", updatedProject.getDescription(), "Project description should match the updated value");

        // Verify that the save method was called
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void addUserToProject_ShouldAddUser_WhenUserNotInTeam() throws Exception {
        // Arrange
        Long projectId = 1L;
        Long userId = 2L;

        Project project = new Project();
        project.setId(projectId);
        project.setTeam(new ArrayList<>());

        User user = new User();
        user.setId(userId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userService.findUserById(userId)).thenReturn(user);

        // Act
        projectServiceImp.addUserToProject(projectId, userId);

        // Assert
        assertTrue(project.getTeam().contains(user), "User should be added to the project team");
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void addUserToProject_ShouldNotAddUser_WhenUserAlreadyInTeam() throws Exception {
        // Arrange
        User user = new User();
        user.setId(userId);

        Project project = new Project();
        project.setId(projectId);
        project.setTeam(new ArrayList<>(Collections.singletonList(user)));

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userService.findUserById(userId)).thenReturn(user);

        // Act
        projectServiceImp.addUserToProject(projectId, userId);

        // Assert
        assertEquals(1, project.getTeam().size(), "User should not be added again to the project team");
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void removeUserFromProject_ShouldRemoveUser_WhenUserIsInTeam() throws Exception {
        // Arrange
        User user = new User();
        user.setId(userId);

        Project project = new Project();
        project.setId(projectId);
        project.setTeam(new ArrayList<>(Collections.singletonList(user)));
        Chat chat = new Chat();
        chat.setUsers(new ArrayList<>(Collections.singletonList(user)));
        project.setChat(chat);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userService.findUserById(userId)).thenReturn(user);

        // Act
        projectServiceImp.removeUserFromProject(projectId, userId);

        // Assert
        assertFalse(project.getTeam().contains(user), "User should be removed from project team");
        assertFalse(project.getChat().getUsers().contains(user), "User should be removed from chat");
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void removeUserFromProject_ShouldDoNothing_WhenUserIsNotInTeam() throws Exception {
        // Arrange
        User user = new User();
        user.setId(userId);

        Project project = new Project();
        project.setId(projectId);
        project.setTeam(new ArrayList<>());
        Chat chat = new Chat();
        chat.setUsers(new ArrayList<>());
        project.setChat(chat);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userService.findUserById(userId)).thenReturn(user);

        // Act
        projectServiceImp.removeUserFromProject(projectId, userId);

        // Assert
        assertTrue(project.getTeam().isEmpty(), "Project team should remain empty");
        assertTrue(project.getChat().getUsers().isEmpty(), "Chat users should remain empty");
        verify(projectRepository, never()).save(project);
    }
}
