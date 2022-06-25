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
    if (storedSteamAppIdsInTheCart === null || storedSteamAppIdsInTheCart!.length === 0) {
      this.cartItemsCounter = 0;
      return this.cartItemsCounter;
    } else {
      let idCounter = localStorage.getItem('cart')?.split(',');
      let idCounterLength = idCounter?.length || 0;
      this.cartItemsCounter = idCounterLength;
      return this.cartItemsCounter;
    }

  }

  addgamesToCart(steamAppId: number) {
    let storedSteamAppIdsInTheCart = localStorage.getItem('cart');
    if (storedSteamAppIdsInTheCart !== null && storedSteamAppIdsInTheCart.length > 0) {
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

  getAllIDFromCart() {
    let storedSteamAppIdsInTheCart = localStorage.getItem('cart');
    let allIdNumberArray: number[] = [];
    if (storedSteamAppIdsInTheCart !== null) {
      let allIdArray: string[] = localStorage.getItem('cart')?.split(',') || [];
      for (let id of allIdArray) {
        let idInNumberFormat = Number(id);
        allIdNumberArray.push(idInNumberFormat)
      }

      return allIdNumberArray;
    }
    return []

  }

  removeItemFromCart(steam_appid: number) {
    let idsInLocalStorage: number[] = this.getAllIDFromCart();
    let idsWithoutRemovedElement = idsInLocalStorage.filter((id) => { return id !== steam_appid });
    let resultToSavingInString = '';

    for (let i = 0; i < idsWithoutRemovedElement.length; i++) {
      if (i === 0) {
        resultToSavingInString += idsWithoutRemovedElement[i];
      } else {
        resultToSavingInString += ',' + idsWithoutRemovedElement[i];

      }
    }
    localStorage.setItem('cart', resultToSavingInString);
  }
  
}



