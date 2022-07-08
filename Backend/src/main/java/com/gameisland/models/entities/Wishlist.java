package com.gameisland.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(name = "wishlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist extends BusinessObject {
    private Long id;
    private Long appId;
    private String name;
    private String headerImage;

    @OneToOne(mappedBy = "wishlist")
    private User user;

}


