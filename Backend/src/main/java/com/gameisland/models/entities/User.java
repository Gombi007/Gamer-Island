package com.gameisland.models.entities;


import com.gameisland.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@NamedNativeQuery(name = "User.findUserDTOByUserUUID",
        query = "SELECT user_name AS userName, email, avatar, balance, useruuid AS userUUID FROM users WHERE useruuid = :UUID",
        resultSetMapping = "Mapping.UserDTO")

@SqlResultSetMapping(name = "Mapping.UserDTO",
        classes = @ConstructorResult(targetClass = UserDTO.class,
                columns = {
                        @ColumnResult(name = "userName", type = String.class),
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name = "avatar", type = String.class),
                        @ColumnResult(name = "balance", type = Double.class),
                        @ColumnResult(name = "userUUID", type = String.class)}))

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
    private Double balance;
    private String userUUID;
    private Timestamp lastBalanceUpdate;
    private Timestamp lastLoginDate;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<SteamGame> ownedGames;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_wishlist_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<SteamGame> wishlist;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GameStat> gameStats;

}


