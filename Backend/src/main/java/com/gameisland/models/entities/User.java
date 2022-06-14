package com.gameisland.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BusinessObject {
    private String userName;
    private String password;
    private String email;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

}


