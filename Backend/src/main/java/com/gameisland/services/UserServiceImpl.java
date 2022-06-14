package com.gameisland.services;

import com.gameisland.exceptions.ResourceAlreadyExists;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.Login;
import com.gameisland.models.entities.User;
import com.gameisland.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AzureService azureService;
    private final IdGeneratorService idGeneratorService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createANewUser(User user) {
        boolean isExistingUser = userRepository.findExistByName(user.getUserName());
        if (isExistingUser) {
            throw new ResourceAlreadyExists("This username already exists in the database." + user.getUserName());
        }

        Set<String> existingIds = userRepository.getAllExistingUUID();
        String uniqueId = idGeneratorService.getNewRandomGeneratedId(existingIds);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
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
    public Object login(Login login) {
        boolean isExistingUser = userRepository.findExistByName(login.getUserName());
        if (isExistingUser) {
            String hashedUserPassword = userRepository.getPasswordHash(login.getUserName());
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
