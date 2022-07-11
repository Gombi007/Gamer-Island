package com.gameisland.services;

import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.SteamGameDTO;
import com.gameisland.models.entities.SteamGame;
import com.gameisland.repositories.SteamGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional
public class SteamGameServiceImpl implements SteamGameService {
    private final SteamApiAllProductsService steamApiAllProductsService;
    private final SteamApiDetailService steamApiDetailService;
    private final SteamGameRepository steamGameRepository;

    @Autowired
    public SteamGameServiceImpl(SteamApiAllProductsService steamApiAllProductsService, SteamApiDetailService steamApiDetailService, SteamGameRepository steamGameRepository) {
        this.steamApiAllProductsService = steamApiAllProductsService;
        this.steamApiDetailService = steamApiDetailService;
        this.steamGameRepository = steamGameRepository;
    }

    @Override
    public SteamGameDTO getGameDetailsByAppId(Long appId) {
        if (appId != null) {
            try {
                SteamGame game = steamGameRepository.gameByAppId(appId).get();
                return SteamGameDTO.convertToGameDto(game);
            } catch (NoSuchElementException exception) {
                throw new ResourceNotFoundException("No game in the database with this App ID: " + appId);
            }
        }
        throw new ResourceNotFoundException("App ID mustn't be NULL: " + appId);
    }

    @Override
    public Set<SteamGameDTO> getAllCartGames(Long[] steamAppIds) {
        Set<SteamGameDTO> result = new HashSet<>();

        if (steamAppIds != null && steamAppIds.length > 0) {
            for (int i = 0; i < steamAppIds.length; i++) {
                result.add(getGameDetailsByAppId(steamAppIds[i]));

            }
        }
        return result;
    }

    @Override
    public Set<String> getAllGenres() {
        Set<String> allGenresInDB = new TreeSet<>();
        List<String> genres = steamGameRepository.allGenres();
        for (int i = 0; i < genres.size(); i++) {
            String[] tmp = genres.get(i).split(";");
            for (int j = 0; j < tmp.length; j++) {
                if (tmp[j].length() > 1) {
                    allGenresInDB.add(tmp[j]);
                }
            }
        }
        return allGenresInDB;
    }

    @Override
    public Map<String, Double> getMinAndMaxPrice() {
        Double minPrice = steamGameRepository.getMinPrice();
        Double maxPrice = steamGameRepository.getMaxPrice();
        Map<String, Double> result = new HashMap<>();
        if (minPrice.isNaN()) {
            minPrice = 0.0;
        }
        if (maxPrice.isNaN()) {
            maxPrice = 999999.0;
        }
        result.put("min", minPrice);
        result.put("max", maxPrice);
        return result;
    }


    @Override
    public Page<SteamGameDTO> getGamesByNameOrGenreOrDescriptionAndConvertDto(int page, int size, String attribute, String attributeVale) {
        Boolean isEmptyDatabase = steamGameRepository.findAll().isEmpty();
        if (!isEmptyDatabase) {

            return getGamesInPaginationByAttribute(page, size, attribute, attributeVale);

        }
        throw new ResourceNotFoundException("Empty database");
    }

    private Page<SteamGameDTO> getGamesInPaginationByAttribute(int page, int size, String attribute, String attributeValue) {
        if (!attribute.isEmpty() && !attributeValue.isEmpty()) {
            attribute = attribute.toLowerCase(Locale.ROOT);
            attributeValue = attributeValue.toLowerCase(Locale.ROOT);
        }

        Page<SteamGame> sortedAndPagedGames = null;
        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        if (attribute.equals("")) {
            Pageable pageRequest = PageRequest.of(page, size, sort);
            sortedAndPagedGames = steamGameRepository.findAll(pageRequest);
        }

        if (attribute.equals("name")) {
            Pageable pageRequest = PageRequest.of(page, size, sort);
            sortedAndPagedGames = steamGameRepository.findAllByNameContainsIgnoreCase(attributeValue, pageRequest);
        }

        if (attribute.equals("description")) {
            Pageable pageRequest = PageRequest.of(page, size, sort);
            sortedAndPagedGames = steamGameRepository.findAllByDetailedDescriptionContainsIgnoreCase(attributeValue, pageRequest);
        }

        if (attribute.equals("genre")) {
            Pageable pageRequest = PageRequest.of(page, size, sort);
            sortedAndPagedGames = steamGameRepository.findAllByGenresContainsIgnoreCase(attributeValue, pageRequest);
        }

        if (attribute.equals("price")) {
            Sort sortByLowToHighPrice = Sort.by(Sort.Direction.ASC, "price");
            Pageable pageRequest = PageRequest.of(page, size, sortByLowToHighPrice);
            String[] minAndMaxPrice = attributeValue.toLowerCase(Locale.ROOT).split("-");
            Double minPrice = 0.0;
            Double maxPrice = 0.0;
            try {
                minPrice = Double.parseDouble(minAndMaxPrice[0]);
                maxPrice = Double.parseDouble(minAndMaxPrice[1]);
            } catch (Exception exception) {
                log.error("Error in parsing prices: {}", exception.getMessage());
            }
            sortedAndPagedGames = steamGameRepository.findAllByPriceBetween(minPrice, maxPrice, pageRequest);
        }

        List<SteamGame> gamesInaPage = sortedAndPagedGames.getContent();
        ArrayList<SteamGameDTO> concertToDto = new ArrayList<>();
        for (int i = 0; i < gamesInaPage.size(); i++) {
            concertToDto.add(SteamGameDTO.convertToGameDto(gamesInaPage.get(i)));
        }
        Page<SteamGameDTO> resultPageWithDto = new PageImpl<>(concertToDto, sortedAndPagedGames.getPageable(), sortedAndPagedGames.getTotalElements());

        return resultPageWithDto;
    }

    // Services for admin only
    @Override
    public void saveProductsInAFileViaSteamApi() {
        steamApiAllProductsService.getAllSteamProducts();

    }

    @Override
    public void saveSteamProductsFromFileDBToDatabase(Integer limit) {
        Set<Long> existingAppIds = steamGameRepository.allExistingSteamAppId();
        Set<Long> appids = steamApiAllProductsService.getAllSteamAppIdFromFileDB(limit, existingAppIds);

        Iterator<Long> iterator = appids.iterator();
        int counter = 1;
        while (iterator.hasNext()) {
            SteamGame game = steamApiDetailService.saveSteamGamesIntoTheDatabase(iterator.next());
            if (game != null) {
                steamGameRepository.save(game);
                log.warn("{}. Game saved: {}", counter, game.getName());
                counter++;
            }
        }
        steamApiAllProductsService.removeAllSavedAndNonGameElementFromSteamAppJson();

    }

    @Override
    public Map<String, String> removeAGamePermanentlyFromTheDatabaseByAppId(Long appid) {
        SteamGame game = null;
        Map<String, String> result = new HashMap<>();
        try {
            game = steamGameRepository.gameByAppId(appid).get();
        } catch (NoSuchElementException exception) {
            throw new ResourceNotFoundException("The game is not exist with this appid: " + appid);
        }

        if (game != null) {
            steamGameRepository.deleteUserGameEntriesByGameId(game.getId());
            steamGameRepository.delete(game);
            result.put(game.getSteamAppId().toString(), "Game was removed from the DB");
            return result;
        }
        throw new ResourceNotFoundException("The game is not exist with this appid: " + game.getSteamAppId());

    }
}
