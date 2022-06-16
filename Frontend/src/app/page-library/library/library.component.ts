import { getLocaleDayNames } from '@angular/common';
import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Game } from '../../game.model';
import { HttpClient } from '@angular/common/http';
import { catchError, EMPTY, fromEvent, map, of, Subject, switchMap, tap } from 'rxjs';
import { GameDetails } from '../../game-details.model';
import { STRINGS } from 'src/app/strings.enum';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { Router } from '@angular/router';
import { GlobalService } from 'src/app/global.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {

  innerHeight!: number;
  headerHeight: number = 144;
  games: Game[] = [];
  gamesClone: Game[] = [];
  isPending: Boolean = false;

  @Output()
  gameDetailsByAppId = new EventEmitter();


  constructor(private http: HttpClient, private author: AuthorizationService, private route:Router, private global:GlobalService) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.librayGame$.subscribe();

    //@ts-ignore
    this.librayGame$.next();

  }

  librayGame$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() => this.http.get(STRINGS.API_LIBRARY, this.author.TokenForRequests())),
    tap((data: any) => {
      this.isPending = false;
      this.games = data;
      this.gamesClone = this.games;
    }),
    catchError(error => {
      this.global.experiedSession = true;
      let message = error.error.error_message;
      if (message.includes("Token has expired")) {      
        this.route.navigate(['login']);        
      }
      return EMPTY;
    })
  );


  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  searching(searching: string) {
    this.gamesClone = this.games;
    let filteredResult = this.gamesClone.filter(game => {
      return game.name.toLowerCase().includes(searching);
    });

    this.gamesClone = filteredResult;

  }
  getGameNameForShowing(gameAppid: number) {

    let gameDetails = new GameDetails();
    let detail$ = this.http.get(STRINGS.API_GAMES_DETAILS + gameAppid, this.author.TokenForRequests()).pipe(
      tap((data: any) => {
        gameDetails.id = data.id;
        gameDetails.steam_appid = data.steam_appid;
        gameDetails.success = data.success;
        gameDetails.name = data.name;
        gameDetails.required_age = data.required_age;
        gameDetails.is_free = data.is_free;
        gameDetails.detailed_description = data.detailed_description;
        gameDetails.about_the_game = data.about_the_game;
        gameDetails.short_description = data.short_description;
        gameDetails.supported_languages = data.supported_languages;
        gameDetails.header_image = data.header_image;
        gameDetails.website = data.website;
        gameDetails.developers = data.developers;
        gameDetails.publishers = data.publishers;
        gameDetails.price_in_final_formatted = data.price_in_final_formatted;
        gameDetails.platforms = data.platforms;
        gameDetails.metacritics = data.metacritics;
        gameDetails.screenshot_urls = data.screenshot_urls;
        gameDetails.genres = data.genres;
      }),
      tap(() => { this.gameDetailsByAppId.emit(gameDetails) })
    ).subscribe();

  }


}
