import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { Game } from '../game.model';
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
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT+STRINGS.SEARCH_BAR__HEIGHT_FOR_CONTENT;
  isPending = false;
  wishlist:Game[] =[];
  wishlistGenres = ["Action","Strategy","Indie"];
  defaultGenre ="All Genres"
  search = new FormGroup(
    {
      name: new FormControl("", [Validators.minLength(3), Validators.maxLength(22)]),
      genres: new FormControl(""),
    }
  );

  constructor(private http:HttpClient, private global:GlobalService, private router:Router,private author:AuthorizationService) { }

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
        this.http.get(STRINGS.API_USER_WISHLIST+this.global.getUUIDFromLocalStore(), this.author.TokenForRequests())),
      tap((data: any) => {
        this.isPending = false;
        this.wishlist = data;
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

}
