import { HttpClient } from '@angular/common/http';
import { AfterViewChecked, Component, ElementRef, EventEmitter, HostListener, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
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
  @Input()
  filterFromStoreSearch = [];
  @Output() selectedGameAppid = new EventEmitter<number>();
  //@ts-ignore
  @ViewChild('scrollableDiv', { read: ElementRef }) scrollableDiv: ElementRef<any>;
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  gamesFromDatabase: GameDetails[] = [];
  isPending = false;
  totalElements: number = 0;
  count: number = 0;
  itemsPerPage = 40;


  constructor(private http: HttpClient, private author: AuthorizationService, private route: Router, private global: GlobalService) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.filteredShopGames$.subscribe();
    //@ts-ignore
    this.filteredShopGames$.next();
  }

  ngOnChanges(changes: SimpleChanges) {
    let filter = changes['filterFromStoreSearch'];
    if (!filter.firstChange) {
      this.filterByGenreOrSearchStoreComponent(filter.currentValue);
    }
  }

  get actualFilterAttribute(): string {
    return this.global.filteredShopGamesBy[0];
  }

  get actualFilterAttributeValue(): string {
    return this.global.filteredShopGamesBy[1];
  }

  get currentPage(): number {
    return this.global.storeCurrentPage;
  }

  ngAfterViewChecked() {
    this.scrollableDiv.nativeElement.scrollTop = this.global.storeCurrentYPosition;
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }
  filteredShopGames$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.get(STRINGS.API_GAMES_FILTER_BY_ATTRIBUTE + "?page=" + (this.global.storeCurrentPage - 1) + "&size=" + this.itemsPerPage + "&attribute=" + this.actualFilterAttribute + "&attributeValue=" + this.actualFilterAttributeValue, this.author.TokenForRequests())),
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
    if (game.price_in_final_formatted > 0 && !game.is_free) {
      return 1;
    }

    if (game.price_in_final_formatted === 0 && game.is_free) {
      return 2;
    }

    if (game.price_in_final_formatted === 0 && !game.is_free) {
      return 3;
    }
    return 0;
  }
  onTableDataChange(event: any) {
    this.global.storeCurrentPage = event;
    //@ts-ignore
    this.filteredShopGames$.next();
    this.scrollableDiv.nativeElement.scrollTop = 0
    this.global.storeCurrentYPosition = 0;
  }


  goToGameDeatil(steam_appid: number) {
    this.global.storeCurrentYPosition = this.scrollableDiv.nativeElement.scrollTop;
    this.selectedGameAppid.emit(steam_appid);
    this.route.navigate(["store/", steam_appid])
  }

  filterByGenreOrSearchStoreComponent(genreValueOrArray: any) {
    this.global.filteredShopGamesBy = [];
    this.global.storeCurrentPage = 1;
    this.global.storeCurrentYPosition = 0;
    if (Array.isArray(genreValueOrArray)) {
      this.global.filteredShopGamesBy.push(genreValueOrArray[0]);
      this.global.filteredShopGamesBy.push(genreValueOrArray[1]);
    } else {
      this.global.filteredShopGamesBy.push('genre');
      this.global.filteredShopGamesBy.push(genreValueOrArray);
    }
    //@ts-ignore
    this.filteredShopGames$.next();
  }

}
