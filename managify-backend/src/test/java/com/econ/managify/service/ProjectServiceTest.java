package com.econ.managify.service;


import com.econ.managify.model.User;
import com.econ.managify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    public void createAnUser() throws Exception{
        User newUser = new User();
        newUser.setEmail("UserServiceTest@gmail.com");
        newUser.setPassword("123123");
        newUser.setFullName("User Service Test");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);

        User savedUser = UserServiceImp.

    }
}
