package com.gameisland.services;

import com.gameisland.exceptions.ResourceAlreadyExists;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.Login;
import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.User;
import com.gameisland.repositories.RoleRepository;
import com.gameisland.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the DB");
        } else {
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    private final UserRepository userRepository;
    private final IdGeneratorService idGeneratorService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final AzureService azureService;

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

    //Only Admin methods

    @Override
    public ArrayList<User> getAllUserFromDatabase() {
        return userRepository.findAll();
    }

    @Override
    public void removeAUserPermanently(String uuid) {
        User user = userRepository.getUserByUUID(uuid).get();
        userRepository.deleteUserGameEntriesByUserId(user.getId());
        userRepository.delete(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String uuid, String roleName) {
        User user = userRepository.getUserByUUID(uuid).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public ArrayList<Role> getAllRoles() {
        return roleRepository.findAll();
    }


}
