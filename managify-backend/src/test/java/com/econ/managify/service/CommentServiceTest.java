package com.econ.managify.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.econ.managify.interfaces.CommentService;
import com.econ.managify.model.Comments;
import com.econ.managify.model.Issues;
import com.econ.managify.model.User;
import com.econ.managify.repository.CommentRepository;
import com.econ.managify.repository.IssueRepository;
import com.econ.managify.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private Issues testIssue;
    private User testUser;
    private Comments testComment;

    @BeforeEach
    public void setUp() {

        testIssue = new Issues();
        testIssue.setId(1L);
        testIssue.setComments(new ArrayList<>());

        testUser = new User();
        testUser.setId(1L);

        testComment = new Comments();
        testComment.setId(1L);
        testComment.setIssues(testIssue);
        testComment.setUser(testUser);
        testComment.setContent("This is a test comment");
    }

    @Test
    public void createComment_Success_ReturnsSavedComment() throws Exception {
        // Arrange
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(commentRepository.save(any(Comments.class))).thenReturn(testComment);

        // Act
        Comments createdComment = commentService.createComment(1L, 1L, "This is a test comment");

        // Assert
        assertNotNull(createdComment);
        assertEquals(testComment.getContent(), createdComment.getContent());
        assertEquals(testIssue.getId(), createdComment.getIssues().getId());
        assertEquals(testUser.getId(), createdComment.getUser().getId());
        verify(commentRepository, times(1)).save(any(Comments.class));
    }

    @Test
    public void createComment_IssueNotFound_ThrowsException() {
        // Arrange
        when(issueRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(Exception.class, () -> {
            commentService.createComment(1L, 1L, "This is a test comment");
        });
    }

    @Test
    public void createComment_UserNotFound_ThrowsException() {
        // Arrange
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> {
            commentService.createComment(1L, 1L, "This is a test comment");
        });
    }

    @Test
    public void deleteComment_Success() throws Exception {
        // Arrange
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        commentService.deleteComment(1L, 1L);

        // Assert
        verify(commentRepository, times(1)).delete(testComment);
    }

    @Test
    public void deleteComment_CommentNotFound_ThrowsException() {
        // Arrange
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(Exception.class, () -> {
            commentService.deleteComment(1L, 1L);
        });
    }

    @Test
    public void deleteComment_UserNotFound_ThrowsException() {
        // Arrange
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> {
            commentService.deleteComment(1L, 1L);
        });
    }

    @Test
    public void deleteComment_UserDoesNotHavePermission_ThrowsException() {
        // Arrange
        User anotherUser = new User();
        anotherUser.setId(2L);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));
        when(userRepository.findById(2L)).thenReturn(Optional.of(anotherUser));

        // Act & Assert
        assertThrows(Exception.class, () -> {
            commentService.deleteComment(1L, 2L);
        });
    }

    @Test
    public void findCommentByIssue_ReturnsCommentList() {
        // Arrange
        List<Comments> commentsList = new ArrayList<>();
        commentsList.add(testComment);
        when(commentRepository.findByIssuesId(1L)).thenReturn(commentsList);

        // Act
        List<Comments> foundComments = commentService.findCommentByIssue(1L);

        // Assert
        assertNotNull(foundComments);
        assertFalse(foundComments.isEmpty());
        assertEquals(1, foundComments.size());
        assertEquals(testComment.getContent(), foundComments.get(0).getContent());
    }
}

