import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subject, switchMap, tap } from 'rxjs';
import { GameDetails } from '../game-details.model';
import { STRINGS } from '../strings.enum';

@Component({
  selector: 'app-page-store',
  templateUrl: './page-store.component.html',
  styleUrls: ['./page-store.component.css']
})
export class PageStoreComponent implements OnInit {

  gamesFromDatabase: GameDetails[] = [];
  isPending = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.shopGames$.subscribe();
    //@ts-ignore
    this.shopGames$.next();
  }

  
  shopGames$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() => this.http.get(STRINGS.API_ALL_GAMES_FOR_SHOP)),
    tap((data: any) => {
      this.isPending = false;
      this.gamesFromDatabase = data;
    })
  );





}
