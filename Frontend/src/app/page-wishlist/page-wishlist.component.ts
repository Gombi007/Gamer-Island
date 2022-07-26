import { Options } from '@angular-slider/ngx-slider';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY, interval, Subject, Subscription, switchMap, takeWhile, tap, timeout } from 'rxjs';
import { GameDetails } from '../game-details.model';
import { GlobalService } from '../global.service';
import { AuthorizationService } from '../login/service/authorization.service';
import { STRINGS } from '../strings.enum';

@Component({
  selector: 'app-page-wishlist',
  templateUrl: './page-wishlist.component.html',
  styleUrls: ['./page-wishlist.component.css']
})
export class PageWishlistComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT + STRINGS.SEARCH_BAR__HEIGHT_FOR_CONTENT;
  isPending = false;
  wishlist: GameDetails[] = [];
  wishlistGenres = [];
  defaultGenre = "All Genres"
  wishlistPictureChange$: Subscription = new Subscription();
  acutalHeaderImageBeforeChangePicture: string = "";
  deleteOptions = {};
  search = new FormGroup(
    {
      name: new FormControl("", [Validators.minLength(3), Validators.maxLength(22)]),
      genres: new FormControl(""),
    }
  );

  constructor(private http: HttpClient, private global: GlobalService, private router: Router, private author: AuthorizationService) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.getUserWishlistGames$.subscribe();
    //@ts-ignore
    this.getUserWishlistGames$.next();
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  getUserWishlistGames$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.get(STRINGS.API_USER_WISHLIST + this.global.getUUIDFromLocalStore(), this.author.TokenForRequests())),
    tap((data: any) => {
      this.isPending = false;
      this.wishlist = data[0];
      this.wishlistGenres = data[1];
    }),

    catchError(error => {
      let message = error.error.error_message;
      if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.router.navigate(['login']);
      }
      this.isPending = false;
      return EMPTY;
    })
  );

  removeWislistItremFromUser$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.delete(STRINGS.API_USER_WISHLIST + this.global.getUUIDFromLocalStore(), this.deleteOptions)
    ),
    catchError(error => {
      console.log(error);

      let message = error.error.error_message;
      if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.router.navigate(['login']);
      }
      this.isPending = false;
      return EMPTY;
    })
  );


  //convenience getter for easy access to form fields
  get searchInputError(): { [key: string]: AbstractControl; } {
    return this.search.controls;
  }

  getSearchData() {
    if (this.search.valid) {
      let name = this.search.get('name')?.value;
      let genre = this.search.get('genres')?.value;
      let attributeAndValue = [name, genre];
      console.log(attributeAndValue);
    }
  }


  changeGamePicureStart(appId: any) {
    let gameOrNot = this.wishlist.find(game => game.steam_appid === appId);

    if (gameOrNot !== undefined) {
      let game: GameDetails;
      let delayObservable$ = interval(1000);
      let i = 0
      game = gameOrNot;
      this.acutalHeaderImageBeforeChangePicture = game.header_image;
      if (i < game.screenshot_urls.length) {
        game.header_image = game.screenshot_urls[i]
        i++;
      }
      this.wishlistPictureChange$ = delayObservable$.subscribe(() => {
        if (i < game.screenshot_urls.length) {
          game.header_image = game.screenshot_urls[i]
          i++;
        }
      });
    }
  }

  changeGamePicureStop(appId: any) {
    let gameOrNot = this.wishlist.find(game => game.steam_appid === appId);
    if (gameOrNot !== undefined) {
      this.wishlistPictureChange$.unsubscribe();
      let game: GameDetails;
      game = gameOrNot;
      game.header_image = this.acutalHeaderImageBeforeChangePicture;
    }
  }

  removeWishlistItem(appId: number) {
    let removableWishlistItems: number[] = [];
    removableWishlistItems.push(appId);

    this.deleteOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.author.getToken()}`
      })
      , body: removableWishlistItems
    };

    this.removeWislistItremFromUser$.subscribe();
    //@ts-ignore
    this.removeWislistItremFromUser$.next();

    let filteredWishlist = this.wishlist.filter((e) => { return e.steam_appid !== appId });
    this.wishlist = filteredWishlist;

  }


}
