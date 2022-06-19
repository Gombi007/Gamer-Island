import { HttpBackend, HttpClient, HttpClientModule } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { BehaviorSubject, Subject, switchMap, tap } from 'rxjs';
import { AuthorizationService } from './login/service/authorization.service';
import { STRINGS } from './strings.enum';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  experiedSession = false;
  usernameFromServer = 'PROFILE'

  constructor(private http:HttpClient, private author:AuthorizationService) { }

  getUsernameAndBalanceByUUID() {
    let uuid = localStorage.getItem('user_id');
    return this.http.get<any>(STRINGS.API_USER_GET_USERNAME_BY_UUID + uuid, this.author.TokenForRequests())
  }


}
