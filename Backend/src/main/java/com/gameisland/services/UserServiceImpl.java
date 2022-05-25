package com.gameisland.services;

import com.gameisland.models.entities.Game;
import com.gameisland.models.entities.User;
import com.gameisland.repositories.GameRepository;
import com.gameisland.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }


    @Override
    public User createANewUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User addAGameToUser(Long userId, Long gameID) {
        Set<Game> gameSet = new HashSet<>();
        Game game = gameRepository.findById(gameID).get();
        gameSet.add(game);

        User user = userRepository.findById(userId).get();
        user.setOwnedGames(gameSet);

        return userRepository.save(user);
    }
}
