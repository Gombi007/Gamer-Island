import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { STRINGS } from 'src/app/strings.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  constructor(private http: HttpClient) { }

  IsLoggedIn() {
    return localStorage.getItem('token') != null;
  }

  LoginViaBackend(userCredential: any) {
    return this.http.post<any>(STRINGS.API_LOGIN_URL, userCredential);
  }

  RegisterViaBackend(userData: any) {
    return this.http.post<any>(STRINGS.API_REGISTER_URL, userData);
  }

  GetLoggedUserName() {
    let userUUID = localStorage.getItem("user_id"); 
      return this.http.get<any>(STRINGS.API_USER_NAME_URL + userUUID);    
  }

}