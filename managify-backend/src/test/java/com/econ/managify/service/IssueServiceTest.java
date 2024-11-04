package com.econ.managify.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.econ.managify.interfaces.IssueService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.Issues;
import com.econ.managify.model.Project;
import com.econ.managify.model.User;
import com.econ.managify.repository.IssueRepository;
import com.econ.managify.request.IssueRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @InjectMocks
    private IssueService issueService; // Sizin IssueService implementasyonunuz

    private Issues testIssue;
    private Project testProject;
    private User testUser;

    @BeforeEach
    public void setUp() {
        // Test verilerini hazırlayın
        testProject = new Project();
        testProject.setId(1L);

        testUser = new User();
        testUser.setId(1L);

        testIssue = new Issues();
        testIssue.setId(1L);
        testIssue.setProject(testProject);
        testIssue.setAssigned(testUser);
    }

    @Test
    public void getIssueById_IssueExists_ReturnsIssue() throws Exception {
        // Arrange
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));

        // Act
        Issues foundIssue = issueService.getIssueById(1L);

        // Assert
        assertNotNull(foundIssue);
        assertEquals(testIssue.getId(), foundIssue.getId());
    }

    @Test
    public void getIssueById_IssueDoesNotExist_ThrowsException() {
        // Arrange
        when(issueRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> {
            issueService.getIssueById(1L);
        });
    }

    @Test
    public void getIssueByProjectId_ReturnsIssueList() throws Exception {
        // Arrange
        when(projectService.getProjectById(1L)).thenReturn(testProject);
        List<Issues> issuesList = new ArrayList<>();
        issuesList.add(testIssue);
        when(issueRepository.findByProjectId(1L)).thenReturn(issuesList);

        // Act
        List<Issues> foundIssues = issueService.getIssueByProjectId(1L);

        // Assert
        assertNotNull(foundIssues);
        assertFalse(foundIssues.isEmpty());
        assertEquals(1, foundIssues.size());
    }

    @Test
    public void createIssue_Success_ReturnsSavedIssue() throws Exception {
        // Arrange
        IssueRequest request = new IssueRequest();
        request.setTitle("Test Issue");
        request.setDescription("Description of test issue");
        request.setStatus("Open");
        request.setProjectId(1L);
        request.setPriority("High");

        when(projectService.getProjectById(1L)).thenReturn(testProject);
        when(issueRepository.save(any(Issues.class))).thenReturn(testIssue);

        // Act
        Issues createdIssue = issueService.createIssue(request, testUser);

        // Assert
        assertNotNull(createdIssue);
        assertEquals(testIssue.getTitle(), createdIssue.getTitle());
        verify(issueRepository, times(1)).save(any(Issues.class));
    }

    @Test
    public void deleteIssue_Success() throws Exception {
        // Arrange
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));

        // Act
        issueService.deleteIssue(1L, testUser.getId());

        // Assert
        verify(issueRepository, times(1)).deleteById(1L);
    }

    @Test
    public void addUserToIssue_Success() throws Exception {
        // Arrange
        when(userService.findUserById(testUser.getId())).thenReturn(testUser);
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));
        when(issueRepository.save(testIssue)).thenReturn(testIssue);

        // Act
        Issues updatedIssue = issueService.addUserToIssue(1L, testUser.getId());

        // Assert
        assertEquals(testUser, updatedIssue.getAssigned());
        verify(issueRepository, times(1)).save(testIssue);
    }

    @Test
    public void updateStatus_Success() throws Exception {
        // Arrange
        String newStatus = "Closed";
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));
        when(issueRepository.save(testIssue)).thenReturn(testIssue);

        // Act
        Issues updatedIssue = issueService.updateStatus(1L, newStatus);

        // Assert
        assertEquals(newStatus, updatedIssue.getStatus());
        verify(issueRepository, times(1)).save(testIssue);
    }
}

