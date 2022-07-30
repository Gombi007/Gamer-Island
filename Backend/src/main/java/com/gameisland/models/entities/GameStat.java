package com.gameisland.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "game_stats")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameStat extends BusinessObject {
    private Long steamAppId;
    private String badges;
    private String spaceRequired;
    private Long playTimeInSecond;
    private Timestamp lastPlayed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
