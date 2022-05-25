package com.gameisland.services;

import com.gameisland.models.entities.User;
import com.gameisland.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createANewUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
