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
  isPendingMoreData = false;
  nextpage: number = 0;
  totalPages: any;
  size = 24;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.shopGames$.subscribe();
    //@ts-ignore
    this.shopGames$.next();
  }

  onScrollDown(ev: any) {
    this.isPendingMoreData = true;
    console.log(ev)
    this.nextpage++;
    //@ts-ignore
    this.shopGames$.next();
  }



  shopGames$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() => this.http.get(STRINGS.API_ALL_GAMES_FOR_SHOP + "?page=" + this.nextpage + "&size=" + this.size)),
    tap((data: any) => {
      this.totalPages = data.totalPages;
      
      if (this.gamesFromDatabase.length === 0) {
        this.gamesFromDatabase = data.content;
      } else {
        let newPageArray: [] = data.content;
        newPageArray.forEach(user => {
          this.gamesFromDatabase.push(user);
        });
      }
      this.isPending = false;
    }));
}
