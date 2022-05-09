import { getLocaleDayNames } from '@angular/common';
import { Component, HostListener, Input, OnInit } from '@angular/core';
import { Game } from '../game.model';
import { HttpClient } from '@angular/common/http';
import { fromEvent, map, of, Subject, switchMap, tap } from 'rxjs';

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

  getGameNameForShowing(gameAppid:number){
    console.log(gameAppid);
  }

}
