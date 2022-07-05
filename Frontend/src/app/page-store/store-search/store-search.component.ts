import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { GlobalService } from 'src/app/global.service';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { STRINGS } from 'src/app/strings.enum';
import { Options } from '@angular-slider/ngx-slider';

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
      searchGameInput: new FormControl("", [Validators.minLength(3), Validators.maxLength(22)]),
      searchInNameOrDescription: new FormControl("name"),
    }
  );
  genres = [];
  @Output()
  callFilterFunction = new EventEmitter<any>();

  minValuePrice: number = 5;
  maxValuePrice: number = 200;
  optionsPrice: Options = {
    floor: 0,
    ceil: 250
  };

  constructor(private author: AuthorizationService, private http: HttpClient, private global: GlobalService, private route: Router) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.genres$.subscribe();
    //@ts-ignore
    this.genres$.next();

  }

  get actualFilterAttribute(): string {
    let attribute = this.global.filteredShopGamesBy[0].toLocaleUpperCase()
    return attribute;
  }
  get actualFilterAttributeValue(): string {
    return this.global.filteredShopGamesBy[1];
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
    if (this.Search.valid) {
      let attribute = this.Search.get('searchInNameOrDescription')?.value;
      let attributeValue = this.Search.get('searchGameInput')?.value;
      let attributeAndValue = [attribute, attributeValue];
      this.callFilterFunction.emit(attributeAndValue);
    }
  }
  //convenience getter for easy access to form fields
  get searchInpuError(): { [key: string]: AbstractControl; } {
    return this.Search.controls;
  }


  getGenre(genre: any) {
    let attributeAndValue = ['genre', genre];
    this.callFilterFunction.emit(attributeAndValue);
  }

  removeFilter() {
    this.global.storeCurrentPage = 1;
    this.global.storeCurrentYPosition = 0;
    let attributeAndValue = ['', ''];
    this.callFilterFunction.emit(attributeAndValue);
  }

}
