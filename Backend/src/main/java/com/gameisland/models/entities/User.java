package com.gameisland.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
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
    private Set<Game> ownedGames;

    public User() {
    }

    public User(String userName, String password, String email, String role, String avatar, Long balance, String userUUID, Set<Game> ownedGames) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
        this.balance = balance;
        this.userUUID = userUUID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public Set<Game> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(Set<Game> ownedGames) {
        this.ownedGames = ownedGames;
    }
}


