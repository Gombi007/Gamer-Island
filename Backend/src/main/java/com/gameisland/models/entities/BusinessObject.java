package com.gameisland.models.entities;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
