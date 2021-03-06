import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnInit, Input, OnChanges, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap} from 'rxjs';
import { GameDetails } from 'src/app/game-details.model';
import { GlobalService } from 'src/app/global.service';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { STRINGS } from 'src/app/strings.enum';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css'], 
})
export class GameDetailComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  gameSteamAppid: number = 0;
  isPending = false
  errorMessage = ''
  isPendingWishlist = false
  wishlistSteamAppid: number[] = []
  checkUserOwnedTheGameOrOnWishlist = { library: false, wishlist: false }

  game: GameDetails = new GameDetails()
  screenshots: string[] = []
  firstScreenshot: string = "";
  gameName?: string;
  gameDetailedDescHTML="";

  constructor(private route: ActivatedRoute, private router: Router, private global: GlobalService, private http: HttpClient, private author: AuthorizationService,private _sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.route.params.subscribe(params => {
      this.gameSteamAppid = params['steamAppid']
    });
    this.getGameByAppid$.subscribe();
    this.addGameToWishlist$.subscribe();
    this.checkGameOwnedOrOnWishlist$.subscribe();
    //@ts-ignore
    this.getGameByAppid$.next();

  }


  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }


  getGameByAppid$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.get(STRINGS.API_GAMES_DETAILS + this.gameSteamAppid, this.author.TokenForRequests())),
    tap((data: any) => {
      //@ts-ignore
      this.checkGameOwnedOrOnWishlist$.next();
      this.game = data;
      this.screenshots = data.screenshot_urls
      this.firstScreenshot = this.screenshots[0]
      this.gameDetailedDescHTML = this.game.detailed_description;
      this._sanitizer.bypassSecurityTrustHtml(this.gameDetailedDescHTML);
      this.isPending = false;
    }),
    catchError(error => {
      let message = error.error.error_message;
      if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.router.navigate(['login']);
      }
      return EMPTY;
    })
  );

  addGameToWishlist$ = new Subject().pipe(
    tap(() => {
      this.isPendingWishlist = true;
    }),
    switchMap(() =>
      this.http.post(STRINGS.API_USER_WISHLIST + this.global.getUUIDFromLocalStore(), this.wishlistSteamAppid, this.author.TokenForRequests())),
    tap((data: any) => {
      //@ts-ignore
      this.checkGameOwnedOrOnWishlist$.next();
    }),
    catchError(error => {
      let message = error.error.error_message;
      if (message === undefined) {
        this.errorMessage = error.error;
      } else if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.router.navigate(['login']);
      }
      this.isPendingWishlist = false;
      return EMPTY;
    })
  );

  checkGameOwnedOrOnWishlist$ = new Subject().pipe(
    tap(() => {
      this.isPendingWishlist = true;
    }),
    switchMap(() =>
      this.http.get(STRINGS.API_USER_CHECK_WISHLIST_OR_OWNED + this.global.getUUIDFromLocalStore() + '/' + this.gameSteamAppid, this.author.TokenForRequests())),
    tap((data: any) => {
      this.checkUserOwnedTheGameOrOnWishlist = data;
      this.isPendingWishlist = false;
    }),
    catchError(error => {
      let message = error.error.error_message;
      if (message === undefined) {
        this.errorMessage = error.error;
      } else if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.router.navigate(['login']);
      }
      this.isPendingWishlist = false;
      return EMPTY;
    })
  );

  changePic(i: number) {
    this.firstScreenshot = this.screenshots[i];

  }

  nextPic() {
    var screenshotsLength = this.screenshots.length;
    var selectedPicId = this.screenshots.indexOf(this.firstScreenshot);

    if (selectedPicId !== undefined) {
      var id: number = selectedPicId;
      if (id >= 0 && id < screenshotsLength - 1) {
        id += 1;
        this.firstScreenshot = this.screenshots[id]
        document.getElementById(String(id))?.scrollIntoView({ behavior: "smooth", block: "end", inline: "end" });
      } else {
        this.firstScreenshot = this.screenshots[0]
        document.getElementById(String(0))?.scrollIntoView({ behavior: "smooth", block: "end", inline: "end" });
      }
    }
  }

  prevPic() {
    var screenshotsLength = this.screenshots.length;
    var selectedPicId = this.screenshots.indexOf(this.firstScreenshot);

    if (selectedPicId !== undefined) {
      var id: number = selectedPicId;
      if (id > 0 && id < screenshotsLength) {
        id -= 1;
        this.firstScreenshot = this.screenshots[id];
        document.getElementById(String(id))?.scrollIntoView({ behavior: "smooth", block: "end", inline: "end" });
      } else {
        this.firstScreenshot = this.screenshots[screenshotsLength - 1];
        document.getElementById(String(screenshotsLength - 1))?.scrollIntoView({ behavior: "smooth", block: "end", inline: "end" });
      }
    }
  }

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

  addToCart(steamAppId: number) {
    this.global.addgamesToCart(steamAppId);
  }

  addToWishlist(steamAppId: number) {
    this.wishlistSteamAppid.push(steamAppId);
    //@ts-ignore
    this.addGameToWishlist$.next();

  }
}