package com.gameisland.services;

import com.gameisland.exceptions.ResourceAlreadyExistsException;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.User;
import com.gameisland.repositories.RoleRepository;
import com.gameisland.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
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
            throw new ResourceAlreadyExistsException("This username is taken. Please choose another one.");
        }

        Set<String> existingIds = userRepository.getAllExistingUUID();
        String uniqueId = idGeneratorService.getNewRandomGeneratedId(existingIds);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setUserUUID(uniqueId);
        user.setPassword(hashedPassword);

        user.setBalance(1500.0);
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
    public Object getUsernameAndBalanceAndAvatarByUUID(String uuid) {
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }
        String[] usernameAndBalance = userRepository.getUsernameAndBalanceAndAvatar(uuid).split(",");
        Double balance = 0.0;
        try {
            balance = Double.parseDouble(usernameAndBalance[1]);
        } catch (Exception e) {
            log.error("Balance parse error: {}", e.getMessage());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("username", usernameAndBalance[0]);
        result.put("balance", balance);
        result.put("avatar", usernameAndBalance[2]);
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

    @Override
    public void userCartPurchase(String uuid, Long[] steamAppids) {
        System.out.println("UUID to purchase:" + uuid);
        for (int i = 0; i < steamAppids.length; i++) {
            System.out.println("Steam app id to purchase: " + steamAppids[i]);

        }

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
