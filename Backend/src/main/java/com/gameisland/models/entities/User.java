package com.gameisland.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BusinessObject {
    private String userName;
    private String password;
    private String avatar;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> ownedGames;

    public User() {
    }

    public User(String userName, String password, String avatar, Set<Game> ownedGames) {
        this.userName = userName;
        this.password = password;
        this.avatar = avatar;
        this.ownedGames = ownedGames;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Game> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(Set<Game> ownedGames) {
        this.ownedGames = ownedGames;
    }
}


