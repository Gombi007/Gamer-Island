import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor() { }

  TokenForRequests() {
    let auth_token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',     
      'Authorization': `Bearer ${auth_token}`
    });
    const requestOptions = { headers: headers };  
    return requestOptions;
  }


}
