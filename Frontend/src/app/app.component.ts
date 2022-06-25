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
  userObject = { username: 'PROFILE', balance: 0, avatar: '' }
  title = 'Game Island';

  constructor(private global: GlobalService) { }

  getUsername(event: any) {

    this.global.getUUIDFromLocalStore();

    if (this.global.uuid === null) {
      this.userObject.username = 'PROFILE'
    }

    if ( this.global.uuid !== null) {
      let obs = this.global.getUsernameAndBalanceByUUID().subscribe(
        res => {
          this.userObject.username = res.username.toLocaleUpperCase();
          this.userObject.balance = res.balance.toFixed(2);
          this.userObject.avatar = res.avatar;
        }
      );
    }
  }



}
