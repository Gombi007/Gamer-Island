import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
    let body = new FormData();
    body.set('username', userCredential.userName);
    body.set('password', userCredential.password);
    return this.http.post<any>(STRINGS.API_LOGIN_URL, body);
  }

  RegisterViaBackend(userData: any) {
    return this.http.post<any>(STRINGS.API_REGISTER_URL, userData);
  }


}