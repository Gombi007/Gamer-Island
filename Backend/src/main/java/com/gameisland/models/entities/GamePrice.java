package com.gameisland.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_prices")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public Long getSteamAppId() {
        return steamAppId;
    }

    public void setSteamAppId(Long steamAppId) {
        this.steamAppId = steamAppId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getFinal_() {
        return final_;
    }

    public void setFinal_(String final_) {
        this.final_ = final_;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getInitialFormatted() {
        return initialFormatted;
    }

    public void setInitialFormatted(String initialFormatted) {
        this.initialFormatted = initialFormatted;
    }

    public String getFinalFormatted() {
        return finalFormatted;
    }

    public void setFinalFormatted(String finalFormatted) {
        this.finalFormatted = finalFormatted;
    }
}
