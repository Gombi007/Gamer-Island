package com.gameisland.services;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class IdGeneratorService {

    public String getNewRandomGeneratedId(Set<String> existingIds) {
        String uniqueID = UUID.randomUUID().toString();
        while (existingIds.contains(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
        }
        return uniqueID;
    }
}
