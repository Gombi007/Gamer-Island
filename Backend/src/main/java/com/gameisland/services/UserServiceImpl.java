package com.gameisland.services;

import com.gameisland.exceptions.ResourceAlreadyExistsException;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.exceptions.UserBalanceNotEnoughEception;
import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.dto.UserDTO;
import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.SteamGame;
import com.gameisland.models.entities.User;
import com.gameisland.repositories.RoleRepository;
import com.gameisland.repositories.SteamGameRepository;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final SteamGameRepository gameRepository;
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
    public User getUserByName(String username) {
        boolean isExistingUer = userRepository.findByUserName(username) != null;
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this name: " + username);
        }
        return userRepository.findByUserName(username);
    }

    @Override
    public Map<String, String> createUser(User user) {
        User existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser != null) {
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
    public void userCartPurchase(String uuid, Long[] steamAppids) {

        if (steamAppids == null || steamAppids.length == 0) {
            throw new ResourceNotFoundException("No steam app id in the array or null");
        }

        User user = userRepository.getUserByUUID(uuid).get();
        if (user == null) {
            throw new ResourceNotFoundException("No user with this UUID: " + uuid);
        }

        Double userCurrentlyBalance = user.getBalance();
        Set<SteamGame> userCurrentlyGameSet = user.getOwnedGames();

        for (int i = 0; i < steamAppids.length; i++) {
            SteamGame gameFromCart = gameRepository.gameByAppId(steamAppids[i]).get();

            if (gameFromCart != null) {
                boolean isThisGameNotOwnedByUserYet = userCurrentlyGameSet.add(gameFromCart);

                if (isThisGameNotOwnedByUserYet) {
                    boolean userBalanceBiggerThanThisGamePrice = userCurrentlyBalance - gameFromCart.getPrice() > 0.0;

                    if (userBalanceBiggerThanThisGamePrice) {
                        userCurrentlyBalance = userCurrentlyBalance - gameFromCart.getPrice();
                    } else {
                        throw new UserBalanceNotEnoughEception("Sorry, You don't have enough money on your account to buy these games");
                    }
                } else {
                    throw new ResourceAlreadyExistsException("This game already owned by user: " + gameFromCart.getName());
                }
            }
            user.setBalance(userCurrentlyBalance);
            user.setOwnedGames(userCurrentlyGameSet);
            userRepository.save(user);
        }
    }

    @Override
    public List<GameLibraryDetailsDto> libraryDetails(String uuid) {
        List<GameLibraryDetailsDto> result = new ArrayList<>();
        User user = userRepository.getUserByUUID(uuid).get();
        if (user == null) {
            throw new ResourceNotFoundException("No user with this UUID: " + uuid);
        }
        Set<SteamGame> userGameSet = user.getOwnedGames();
        Iterator iterator = userGameSet.iterator();
        while (iterator.hasNext()) {
            SteamGame game = (SteamGame) iterator.next();
            GameLibraryDetailsDto dto = new GameLibraryDetailsDto();
            dto.setAppId(game.getSteamAppId());
            dto.setHeaderImage(game.getHeaderImage());
            dto.setName(game.getName());
            dto.setId(game.getId());
            result.add(dto);
        }

        result.sort(Comparator.comparing(game -> game.getName().toLowerCase(Locale.ROOT)));
        return result;
    }

    @Override
    public UserDTO getUserDataForProfile(String uuid) {
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }
        User user = userRepository.getUserByUUID(uuid).get();
        return UserDTO.convertToDTO(user);
    }

    @Override
    public void updateUserData(UserDTO userDTO) {
        boolean isExistingUer = userRepository.getUserByUUID(userDTO.getUserUUID()).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + userDTO.getUserUUID());
        }
        User user = userRepository.getUserByUUID(userDTO.getUserUUID()).get();

        if (user != null) {
            user.setAvatar(userDTO.getAvatar());
            user.setEmail(userDTO.getEmail());
            userRepository.save(user);
        }
    }

    @Override
    public HashMap<String, String> updateUserBalance(String uuid) {
        HashMap<String, String> result = new HashMap<>();
        long timeLimit = 24;
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }

        User user = userRepository.getUserByUUID(uuid).get();
        Timestamp userLastBalanceUpdateTimestamp = user.getLastBalanceUpdate();

        if (userLastBalanceUpdateTimestamp != null) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime userLastBalanceUpdated = userLastBalanceUpdateTimestamp.toLocalDateTime();
            boolean limitHasExpired = userLastBalanceUpdated.plusHours(timeLimit).isBefore(currentDateTime);
            if (limitHasExpired) {
                user.setLastBalanceUpdate(Timestamp.valueOf(currentDateTime));
                user.setBalance(1500.0);
                userRepository.save(user);
                result.put("balanceUpdate", "Balance top up was success");
                return result;
            } else {
                String limitWillExpired = "";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                limitWillExpired = userLastBalanceUpdated.plusHours(timeLimit).format(formatter);
                result.put("balanceUpdate", limitWillExpired);
                return result;

            }

        } else {
            user.setLastBalanceUpdate(Timestamp.valueOf(LocalDateTime.now()));
            user.setBalance(1500.0);
            userRepository.save(user);
            result.put("balanceUpdate", "Balance top up was success");
            return result;
        }
    }

    @Override
    public Object getUserWishlist(String uuid) {
        List<Object> result = new ArrayList<>();
        List<GameLibraryDetailsDto> dtos = new ArrayList<>();
        Set<String> wishlistAllGenres = new TreeSet<>();
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }
        User user = userRepository.getUserByUUID(uuid).get();
        Iterator<SteamGame> iterator = user.getWishlist().iterator();
        while (iterator.hasNext()) {
            SteamGame game = iterator.next();
            String[] tmp = game.getGenres().split(";");
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i].length() > 1) {
                    wishlistAllGenres.add(tmp[i]);
                }
            }
            GameLibraryDetailsDto dto = new GameLibraryDetailsDto();
            dto.setId(game.getId());
            dto.setName(game.getName());
            dto.setAppId(game.getSteamAppId());
            dto.setHeaderImage(game.getHeaderImage());
            dtos.add(dto);
        }
        dtos.sort(Comparator.comparing(game -> game.getName().toLowerCase(Locale.ROOT)));
        result.add(dtos);
        result.add(wishlistAllGenres);
        return result;
    }


    @Override
    public Object addGamesToWishlist(String uuid, Long[] steamAppids) {
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }
        User user = userRepository.getUserByUUID(uuid).get();

        for (Long steamAppid : steamAppids) {
            try {
                SteamGame game = gameRepository.gameByAppId(steamAppid).get();
                if (!user.getOwnedGames().contains(game)) {
                    user.getWishlist().add(game);
                }

            } catch (NoSuchElementException exception) {
                log.error("This game is not exist: {}", steamAppid);
            }
        }
        userRepository.save(user);
        return null;
    }

    @Override
    public Object removeGameFromWishlist(String uuid, Long[] steamAppids) {
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }
        User user = userRepository.getUserByUUID(uuid).get();

        for (Long steamAppid : steamAppids) {
            try {
                SteamGame game = gameRepository.gameByAppId(steamAppid).get();
                user.getWishlist().remove(game);

            } catch (NoSuchElementException exception) {
                log.error("This game is not exist with this steam appid: {}", steamAppid);
            }
        }
        userRepository.save(user);
        return null;
    }

    @Override
    public Object isUserOwnTheGameOrOnTheWishlist(String uuid, Long steamAppid) {
        boolean isExistingUer = userRepository.getUserByUUID(uuid).isPresent();
        boolean isExistingGame = gameRepository.gameByAppId(steamAppid).isPresent();
        if (!isExistingUer) {
            throw new ResourceNotFoundException("User doesn't exist with this UUID: " + uuid);
        }
        if (!isExistingGame) {
            throw new ResourceNotFoundException("Game doesn't exist with this id: " + steamAppid);
        }
        User user = userRepository.getUserByUUID(uuid).get();
        SteamGame game = gameRepository.gameByAppId(steamAppid).get();
        Map<String, Boolean> result = new HashMap<>();
        if (user.getWishlist().contains(game)) {
            result.put("wishlist", true);
        } else {
            result.put("wishlist", false);
        }

        if (user.getOwnedGames().contains(game)) {
            result.put("library", true);
        } else {
            result.put("library", false);
        }
        return result;
    }

    //Only Admin methods

    @Override
    public List<UserDTO> getAllUserFromDatabase() {
        List<UserDTO> userDTOList = new ArrayList<>();
        ArrayList<User> allUser = userRepository.findAll();

        for (User user : allUser) {
            userDTOList.add(UserDTO.convertToDTO(user));
        }
        return userDTOList;
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
        User user = null;
        Role role = null;
        try {
            user = userRepository.getUserByUUID(uuid).get();
            role = roleRepository.findByName(roleName);
        } catch (NoSuchElementException exception) {
            throw new ResourceNotFoundException("No user with this uuid: " + uuid);
        }
        if (role == null) {
            throw new ResourceNotFoundException("No role with this name: " + roleName);
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public Map<String, String> removeRole(Map<String, String> roleName) {
        Map<String, String> result = new HashMap<>();
        Role role = roleRepository.findByName(roleName.get("roleName"));
        if (role != null) {
            roleRepository.deleteRoleEntriesFromUsersRolesTable(role.getId());
            roleRepository.delete(role);
            result.put(roleName.get("roleName"), "Role was removed");
            return result;
        }
        throw new ResourceNotFoundException("There is no role with this name: " + roleName.get("roleName"));
    }

    @Override
    public List<Object> getAllRoles() {
        ArrayList<Role> allRole = roleRepository.findAll();
        List<Object> result = new ArrayList();
        for (Role role : allRole) {
            HashMap<String, Object> roleInfo = new HashMap<>();
            roleInfo.put(role.getId().toString(), role.getName());
            HashMap<String, String> userInfo = new HashMap<>();
            for (User user : role.getUsers()) {
                userInfo.put(user.getUserName(), user.getUserUUID());
            }
            roleInfo.put("Users", userInfo);
            result.add(roleInfo);

        }
        return result;
    }

}
