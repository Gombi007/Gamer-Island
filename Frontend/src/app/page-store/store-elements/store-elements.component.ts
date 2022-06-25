import { ViewportScroller } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewChecked, Component, ElementRef, EventEmitter, HostListener, OnChanges, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
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
export class StoreElementsComponent implements OnInit, AfterViewChecked {
  @Output() selectedGameAppid = new EventEmitter<number>();
  //@ts-ignore
  @ViewChild('scrollableDiv', { read: ElementRef }) scrollableDiv: ElementRef<any>;
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  gamesFromDatabase: GameDetails[] = [];
  isPending = false;
  currentPage: number = this.global.storeCurrentPage;
  totalElements: number = 0;
  count: number = 0;
  itemsPerPage = 40;

  constructor(private http: HttpClient, private author: AuthorizationService, private route: Router, private global: GlobalService) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.shopGames$.subscribe();
    //@ts-ignore
    this.shopGames$.next();
  }

  ngAfterViewChecked() {
    this.scrollableDiv.nativeElement.scrollTop = this.global.storeCurrentYPosition;
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  onTableDataChange(event: any) {
    this.global.storeCurrentPage = event;
    this.currentPage = this.global.storeCurrentPage;
    //@ts-ignore
    this.shopGames$.next();
    this.scrollableDiv.nativeElement.scrollTop = 0
    this.global.storeCurrentYPosition = 0;
  }


  shopGames$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.get(STRINGS.API_ALL_GAMES_FOR_SHOP + "?page=" + (this.global.storeCurrentPage - 1) + "&size=" + this.itemsPerPage, this.author.TokenForRequests())),
    tap((data: any) => {
      this.totalElements = data.totalElements;
      this.gamesFromDatabase = data.content;
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
    if (game.price_in_final_formatted !== null && game.price_in_final_formatted !== 0) {
      return 1;
    }

    if (game.price_in_final_formatted === 0 && game.genres.includes('Free to Play')) {
      return 2;
    }

    if (game.price_in_final_formatted === 0 && !(game.genres.includes('Free to Play'))) {
      return 3;
    }
    return 0;
  }

  goToGameDeatil(steam_appid: number) {
    this.global.storeCurrentYPosition = this.scrollableDiv.nativeElement.scrollTop;
    this.selectedGameAppid.emit(steam_appid);
    this.route.navigate(["store/", steam_appid])
  }

}
