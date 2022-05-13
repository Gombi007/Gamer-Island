import { getLocaleDayNames } from '@angular/common';
import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Game } from '../game.model';
import { HttpClient } from '@angular/common/http';
import { fromEvent, map, of, Subject, switchMap, tap } from 'rxjs';
import { GameDetails } from '../game-details.model';

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
    switchMap(() => this.http.get("http://localhost:8081/api/library/games")),
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
      return game.name.toLowerCase().includes(searching);
    });

    this.gamesClone = filteredResult;

  }

  getGameNameForShowing(gameAppid: number) {
    let gameDetails = new GameDetails();
    let detail$ = this.http.get("http://localhost:8081/api/library/games/" + gameAppid).pipe(
      tap((data: any) => {
        gameDetails.$name = data.name;
        gameDetails.$steam_appid = data.steam_appid;
        gameDetails.$required_age = data.required_age;
        gameDetails.$short_description = data.short_description;
        gameDetails.$header_image = data.header_image;
        gameDetails.$screenshots = data.screenshots;
      }),
      tap(() => { this.gameDetailsByAppId.emit(gameDetails) })
    ).subscribe();

  }



}
