package com.gameisland.models.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BusinessObject {
    private String name;
}
