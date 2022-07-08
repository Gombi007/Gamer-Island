package com.gameisland.models.entities;


import com.gameisland.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )

    private Set<SteamGame> ownedGames;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    private Wishlist wishlist;

}


