import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, Subject, switchMap, tap } from 'rxjs';
import { GlobalService } from './global.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  userObject = {username:'PROFILE',balance:"0 €"}
  title = 'Game Island';

  constructor(private global: GlobalService, private route: Router) { }

  getUsername(event: any) {

    if (event.constructor.name === 'LoginComponent') {
      this.userObject.username = 'PROFILE'     
    }

    if (this.userObject.username === 'PROFILE' && event.constructor.name !== 'LoginComponent') {
      let obs = this.global.getUsernameAndBalanceByUUID().subscribe(
        res => {
          this.userObject.username = res.username.toLocaleUpperCase();       
          this.userObject.balance = res.balance +' €';    
        }
      );
    }
  }



}
