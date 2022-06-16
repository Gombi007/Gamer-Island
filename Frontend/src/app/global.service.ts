import { HttpBackend, HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthorizationService } from './login/service/authorization.service';
import { STRINGS } from './strings.enum';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  experiedSession = false;
  username = 'USER'

  constructor(private http:HttpClient,private author: AuthorizationService) { }

  GetUsernameByUUID() {
    let uuid= localStorage.getItem('user_id');
    return this.http.get<any>(STRINGS.API_USER_GET_USERNAME_BY_UUID+uuid, this.author.TokenForRequests()).subscribe({
      next:(data)=>{
        this.username = data.userName;      
      }
    });
  }


}
