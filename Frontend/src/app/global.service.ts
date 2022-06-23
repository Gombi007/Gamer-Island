import { HttpBackend, HttpClient, HttpClientModule } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { BehaviorSubject, Subject, switchMap, tap } from 'rxjs';
import { AuthorizationService } from './login/service/authorization.service';
import { STRINGS } from './strings.enum';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  storeCurrentPage: number = 1;
  storeCurrentYPosition: number = 0;
  experiedSession = false;
  usernameFromServer = 'PROFILE'
  uuid: any
  cartItemsCounter = 0;

  constructor(private http: HttpClient, private author: AuthorizationService) {
  }

  getUsernameAndBalanceByUUID() {
    this.uuid = localStorage.getItem('user_id');
    return this.http.get<any>(STRINGS.API_USER_GET_USERNAME_BY_UUID + this.uuid, this.author.TokenForRequests())
  }

  getUUIDFromLocalStore() {
    this.uuid = localStorage.getItem('user_id');
  }

  isThereAnyItemInTheCart() {
    let storedSteamAppIdsInTheCart = localStorage.getItem('cart');
    if (storedSteamAppIdsInTheCart === null) {
      this.cartItemsCounter =0
      return false
    } else {
      return true
    }

  }

  addgamesToCart(steamAppId: number) {
    let storedSteamAppIdsInTheCart = localStorage.getItem('cart');
    if (storedSteamAppIdsInTheCart !== null) {
      let savedGameIds = localStorage.getItem('cart') || '';
      savedGameIds += ',' + steamAppId;
      if (!storedSteamAppIdsInTheCart.includes(steamAppId.toString())) {
        localStorage.setItem('cart', savedGameIds)
        let idCounter = localStorage.getItem('cart')?.split(',');
        let idCounterLength = idCounter?.length || 0;
        this.cartItemsCounter = idCounterLength;
      }
    } else {
      localStorage.setItem('cart', steamAppId.toString())
      this.cartItemsCounter = 1
    }


  }


}
