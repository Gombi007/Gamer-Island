import { HttpClient } from '@angular/common/http';
import { Component, OnInit  } from '@angular/core';
import {  Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { GameDetails } from 'src/app/game-details.model';
import { GlobalService } from 'src/app/global.service';

import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { STRINGS } from 'src/app/strings.enum';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  gamesInTheCart:GameDetails[] =[];
  isPending =false;
  constructor(private global:GlobalService, private router:Router, private http:HttpClient, private author:AuthorizationService) { }

  ngOnInit(): void {
    this.getGameByAppid$.subscribe();
    //@ts-ignore
    this.getGameByAppid$.next();

  }

getGameByAppid$ = new Subject().pipe(
  tap(() => { 
      this.isPending = true;   
    }),
    switchMap(() =>
      this.http.post(STRINGS.API_GAMES_GET_ALL_CART_GAMES, this.global.getAllIDFromCart(),this.author.TokenForRequests())),
    tap((data: any) => {
      this.isPending = false;     
      this.gamesInTheCart = data;
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
  
  

}
