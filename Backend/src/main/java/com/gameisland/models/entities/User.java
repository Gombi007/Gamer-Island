package com.gameisland.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BusinessObject {
    private String userName;
    private String password;
    private String email;
    private String role;
    private String avatar;
    private Long balance;
    private String userUUID;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<SteamGame> ownedGames;

}


