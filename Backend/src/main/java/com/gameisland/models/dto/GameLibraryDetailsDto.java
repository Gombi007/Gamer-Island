package com.gameisland.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameLibraryDetailsDto {
    private Long id;
    private Long appId;
    private String name;
    private String headerImage;
}
