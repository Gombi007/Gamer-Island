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

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final AzureService azureService;
    private final IdGeneratorService idGeneratorService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, AzureService azureService, IdGeneratorService idGeneratorService) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.azureService = azureService;
        this.idGeneratorService = idGeneratorService;
    }


    @Override
    @Transactional
    public User createANewUser(User user) {
        boolean isExistingUser = userRepository.findExistByName(user.getUserName());
        if (isExistingUser) {
            throw new ResourceAlreadyExists("This username already exists in the database." + user.getUserName());
        }

        Set<String> existingIds = userRepository.getAllExistingIds();
        String uniqueId = idGeneratorService.getNewRandomGeneratedId(existingIds);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("user_role");
        user.setBalance(1500L);
        user.setUserUUID(uniqueId);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public Object getUserNameByUUID(String uuid) {
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }
        Map<String, String> result = new HashMap<>();
        result.put("userName", userRepository.getUserNameByUUID(uuid).get());
        return result;
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
    public Object login(Login login) {
        boolean isExistingUser = userRepository.findExistByName(login.getUserName());
        if (isExistingUser) {
            String hashedUserPassword = userRepository.getPasswordHash(login.getUserName());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean passwordOk = passwordEncoder.matches(login.getPassword(), hashedUserPassword);
            if (passwordOk) {
                String userUUID = userRepository.getUserUUID(login.getUserName());
                Map<String, String> userTokenAndUUID = new HashMap<>();
                userTokenAndUUID.put("user_id", userUUID);
                userTokenAndUUID.put("token", azureService.GetJWTFromAzure());
                return userTokenAndUUID;
            }
            throw new ResourceNotFoundException("Wrong password");
        }
        throw new ResourceAlreadyExists("Username doesn't exist." + login.getUserName());

    }


}
