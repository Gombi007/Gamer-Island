package com.gameisland.services;

import com.gameisland.exceptions.ResourceAlreadyExists;
import com.gameisland.exceptions.ResourceNotFoundException;
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
    private final UserRepository userRepository;
    private final IdGeneratorService idGeneratorService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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


    @Override
    public Map<String, String> createANewUser(User user) {
        boolean isExistingUser = userRepository.findExistByName(user.getUserName());
        if (isExistingUser) {
            throw new ResourceAlreadyExists("This username is taken. Please choose another one.");
        }

        Set<String> existingIds = userRepository.getAllExistingUUID();
        String uniqueId = idGeneratorService.getNewRandomGeneratedId(existingIds);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setUserUUID(uniqueId);
        user.setPassword(hashedPassword);

        user.setBalance(1500L);
        Role basicRole = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(basicRole);
        user.setAvatar("https://robohash.org/mail@ashallendesign.co.uk");
        User savedUser = userRepository.save(user);
        Map<String, String> result = new HashMap<>();
        if (savedUser != null) {
            result.put("ok", "New user was saved");

        }
        return result;
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
    public User getUserByName(String username) {
        boolean isExistingUer = userRepository.findByUserName(username) != null;
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + username);
        }
        return userRepository.findByUserName(username);
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
        Set<Role> roleSet = new HashSet<>();
        user.getRoles().forEach(r -> roleSet.add(r));
        roleSet.add(role);
        roleSet.forEach(r -> user.getRoles().add(r));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public ArrayList<Role> getAllRoles() {
        return roleRepository.findAll();
    }


}
