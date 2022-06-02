import { getLocaleDayNames } from '@angular/common';
import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Game } from '../../game.model';
import { HttpClient } from '@angular/common/http';
import { fromEvent, map, of, Subject, switchMap, tap } from 'rxjs';
import { GameDetails } from '../../game-details.model';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {

  innerHeight!: number;
  headerHeight: number = 155;
  games: Game[] = [];
  gamesClone: Game[] = [];
  isPending: Boolean = false;

  @Output()
  gameDetailsByAppId = new EventEmitter();


  constructor(private http: HttpClient) { }

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
    switchMap(() => this.http.get("http://localhost:8081/api/games/library")),
    tap((data: any) => {
      this.isPending = false;
      this.games = data;
      this.gamesClone = this.games;
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
      //why getter with $ not work here     
      return game.name.toLowerCase().includes(searching);
    });

    this.gamesClone = filteredResult;

  }
  getGameNameForShowing(gameAppid: number) {

    let gameDetails = new GameDetails();
    let detail$ = this.http.get("http://localhost:8081/api/games/" + gameAppid).pipe(
      tap((data: any) => {
        gameDetails.$id = data.id;
        gameDetails.$steam_appid = data.steam_appid;
        gameDetails.$success = data.success;
        gameDetails.$name = data.name;
        gameDetails.$required_age = data.required_age;
        gameDetails.$is_free = data.is_free;
        gameDetails.$detailed_description = data.detailed_description;
        gameDetails.$about_the_game = data.about_the_game;
        gameDetails.$short_description = data.short_description;
        gameDetails.$supported_languages = data.supported_languages;
        gameDetails.$header_image = data.header_image;
        gameDetails.$website = data.website;
        gameDetails.$developers = data.developers;
        gameDetails.$publishers = data.publishers;
        gameDetails.$price_in_final_formatted = data.price_in_final_formatted;
        gameDetails.$platforms = data.platforms;
        gameDetails.$metacritics = data.metacritics;
        gameDetails.screenshot_urls = data.screenshot_urls;
        gameDetails.$genres = data.genres;
      }),
      tap(() => { this.gameDetailsByAppId.emit(gameDetails) })
    ).subscribe();

  }


}
