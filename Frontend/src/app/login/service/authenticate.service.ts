import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { STRINGS } from 'src/app/strings.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  constructor(private http:HttpClient) { }
  
  IsLoggedIn(){
    return localStorage.getItem('token') != null;
  }

  LoginViaBackend(userCredential:any){
    let apiLoginUrl = STRINGS.API_LOGIN_URL;
    return this.http.post<any>(apiLoginUrl,userCredential);

  }
  
}