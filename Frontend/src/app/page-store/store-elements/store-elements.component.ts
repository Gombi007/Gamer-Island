import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, EventEmitter, HostListener, OnDestroy, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { GameDetails } from 'src/app/game-details.model';
import { GlobalService } from 'src/app/global.service';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { STRINGS } from 'src/app/strings.enum';

@Component({
  selector: 'app-store-elements',
  templateUrl: './store-elements.component.html',
  styleUrls: ['./store-elements.component.css']
})
export class StoreElementsComponent implements OnInit {
  @Output() selectedGameAppid = new EventEmitter<number>();
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  gamesFromDatabase: GameDetails[] = [];
  isPending = false;
  nextpage: number = 0;
  totalPages: any;
  size = 20;

  constructor(private http: HttpClient, private author: AuthorizationService, private route: Router, private global: GlobalService) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.shopGames$.subscribe();
    //@ts-ignore
    this.shopGames$.next();
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  onScrollDown(ev: any) {
    // if data query is in progress , can't increase the page number with scrolling. 
    if (this.isPending === false) {
      this.nextpage++;
      //@ts-ignore
      this.shopGames$.next();
    }
  }


  shopGames$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.get(STRINGS.API_ALL_GAMES_FOR_SHOP + "?page=" + this.nextpage + "&size=" + this.size, this.author.TokenForRequests())),
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
    }),
    catchError(error => {
      let message = error.error.error_message;
      if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.route.navigate(['login']);
      }
      return EMPTY;
    })
  );

  getPlatform(game: GameDetails, platform: string) {
    if (platform === 'Windows' && game.platforms.includes(platform)) {
      return platform;
    }
    if (platform === 'Mac' && game.platforms.includes(platform)) {
      return platform;
    }

    if (platform === 'Linux' && game.platforms.includes(platform)) {
      return platform;
    }
    return '';
  }

  getGamePrice(game: GameDetails) {
    if (game.price_in_final_formatted !== null && game.price_in_final_formatted !== '') {
      return 1;
    }

    if (game.price_in_final_formatted === '' && game.genres.includes('Free to Play')) {
      return 2;
    }

    if (game.price_in_final_formatted === '' && !(game.genres.includes('Free to Play'))) {
      return 3;
    }
    return 0;
  }

  goToGameDeatil(steam_appid: number) {   
    this.selectedGameAppid.emit(steam_appid);
    this.route.navigate(["store",steam_appid])
  }

}
