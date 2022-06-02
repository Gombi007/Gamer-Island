package com.gameisland.services;

import com.gameisland.exceptions.ResourceAlreadyExists;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.Login;
import com.gameisland.models.entities.Game;
import com.gameisland.models.entities.User;
import com.gameisland.repositories.GameRepository;
import com.gameisland.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final AzureService azureService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, AzureService azureService) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.azureService = azureService;
    }


    @Override
    @Transactional
    public User createANewUser(User user) {
        boolean isExistingUser = userRepository.findExistByName(user.getUserName());
        if (isExistingUser) {
            throw new ResourceAlreadyExists("This username already exists in the database." + user.getUserName());
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("user_role");
        user.setBalance(1500L);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    @Transactional
    public User addAGameToUser(Long userId, Long gameID) {
        Set<Game> gameSet = new HashSet<>();
        Game game = gameRepository.findById(gameID).get();
        User user = userRepository.findById(userId).get();
        gameSet = user.getOwnedGames();
        gameSet.add(game);
        user.setOwnedGames(gameSet);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeAUserPermanently(Long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.deleteUserGameEntriesByUserId(user.getId());
        userRepository.delete(user);
    }

    @Override
    public ArrayList<User> getAllUserFromDatabase() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public String login(Login login) {
        boolean isExistingUser = userRepository.findExistByName(login.getUserName());
        if (isExistingUser) {
            String hashedUserPassword = userRepository.getPasswordHash(login.getUserName());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean passwordOk = passwordEncoder.matches(login.getPassword(), hashedUserPassword);
            if (passwordOk) {
                return azureService.GetJWTFromAzure();
            }
            throw new ResourceNotFoundException("Wrong password");
        }
        throw new ResourceAlreadyExists("Username doesn't exist." + login.getUserName());

    }


}
