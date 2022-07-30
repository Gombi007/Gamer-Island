package com.gameisland.models.dto;

import com.gameisland.models.entities.SteamGame;
import com.gameisland.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userName;
    private String email;
    private String avatar;
    private Double balance;
    private String userUUID;
    private Timestamp lastBalanceUpdate;
    private Timestamp lastLoginDate;
    private Map<Long, String> userGames;
    private Map<Long, String> userWishlist;


    public static UserDTO convertToDTO(User user) {
        //user games only appid and name
        Map<Long, String> userOwnedGames = new HashMap<>();
        for (SteamGame game : user.getOwnedGames()) {
            userOwnedGames.put(game.getSteamAppId(), game.getName());
        }
        //user wislist only appid and name
        Map<Long, String> userOwnedWishlist = new HashMap<>();
        for (SteamGame game : user.getWishlist()) {
            userOwnedWishlist.put(game.getSteamAppId(), game.getName());
        }
        UserDTO userDTO = new UserDTO(user.getUserName(), user.getEmail(), user.getAvatar(), user.getBalance(), user.getUserUUID(), user.getLastBalanceUpdate(), user.getLastLoginDate(), userOwnedGames, userOwnedWishlist);
        return userDTO;
    }
}
