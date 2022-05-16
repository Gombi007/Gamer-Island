package com.gameisland.models.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_prices")
public class GamePrice extends BusinessObject {
    private Long steamAppId;
    private String currency;
    private String initial;
    private String final_;
    private String discountPercent;
    private String initialFormatted;
    private String finalFormatted;

    @OneToOne(mappedBy = "gamePrice")
    private Game game;

    public GamePrice() {
    }

    public GamePrice(Long steamAppId, String currency, String initial, String final_, String discountPercent, String initialFormatted, String finalFormatted) {
        this.steamAppId = steamAppId;
        this.currency = currency;
        this.initial = initial;
        this.final_ = final_;
        this.discountPercent = discountPercent;
        this.initialFormatted = initialFormatted;
        this.finalFormatted = finalFormatted;
    }
}
