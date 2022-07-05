import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { GlobalService } from 'src/app/global.service';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { STRINGS } from 'src/app/strings.enum';

@Component({
  selector: 'app-store-search',
  templateUrl: './store-search.component.html',
  styleUrls: ['./store-search.component.css']
})
export class StoreSearchComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  Search = new FormGroup(
    {
      searchGameInput: new FormControl("", [Validators.required, Validators.minLength(3)]),
      searchInNameOrDescription: new FormControl("name"),
    }
  );
  genres =[];

  constructor(private author: AuthorizationService, private http: HttpClient, private global: GlobalService,private route:Router) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.genres$.subscribe();

    //@ts-ignore
    this.genres$.next();

  }

  genres$ = new Subject().pipe(
    switchMap(() => this.http.get(STRINGS.API_GAMES_GET_ALL_GENRES, this.author.TokenForRequests())),
    tap((data: any) => { 
      this.genres = data;
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

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  getSearchData() {
    console.log(this.Search.get('searchInNameOrDescription')?.value + " " + this.Search.get('searchGameInput')?.value);
  }


}
