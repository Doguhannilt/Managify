package com.econ.managify.repository;


import com.econ.managify.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);


    // Test: Save information using UserRepository | Repository Testing
    @Test
    public void saveAllInformationFromUser() throws Exception {
        // Arrange
        User newUser = new User();
        newUser.setEmail("kindatest@gmail.com");
        newUser.setPassword("123123");
        newUser.setFullName("kinda test");

        // Act
        userRepository.save(newUser);

        // Assert
        User savedUser = userRepository.findByEmail("kindatest@gmail.com");
        assertNotNull(savedUser);
        assertEquals("kinda test", savedUser.getFullName());
        assertEquals("kindatest@gmail.com", savedUser.getEmail());
    }

    // Test: FindByEmail should find an email | Repository Testing
    @Test
    public void findByEmail() throws  Exception{
        String email = "kindatest@gmail.com";
        User findEmail = userRepository.findByEmail(email);
        assertNotNull(findEmail);
        assertEquals("kindatest@gmail.com", findEmail.getEmail());
        logger.info("User Found: {}", findEmail.getFullName());
    }

    // Test: FindAll should find all data | Repository Testing
    @Test
    public void findAllData() throws  Exception{
        List<User> findAll = userRepository.findAll();
        assertNotNull(findAll);
        assertFalse(findAll.isEmpty(), "Func cannot return null");
        logger.info(String.valueOf(findAll.size()));
    }

    // Test: Find Someone By Id | Repository Testing
    @Test
    public void findAnUserById() throws  Exception{
        Optional<User> findId = userRepository.findById(1L);
        assertNotNull(findId, "ID cannot be null");
        logger.info(findId.get().getEmail());
    }

    // Test: Find by empty email | Repository Testing
    @Test
    public void findByEmptyEmail() throws Exception {
        String email = "";
        User findEmail = userRepository.findByEmail(email);
        assertNull(findEmail);
    }

    // Test: Find user by non-existent ID | Repository Testing
    @Test
    public void findUserByNonExistentId() throws Exception {
        // Act
        Optional<User> findId = userRepository.findById(999L);

        // Assert
        assertFalse(findId.isPresent(), "User should not be found");
    }

}


