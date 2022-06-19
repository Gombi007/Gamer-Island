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
  username = 'PROFILE'
  title = 'Game Island';

  constructor(private global: GlobalService, private route: Router) { }

  getUsername(event: any) {

    if (event.constructor.name === 'LoginComponent') {
      this.username = 'PROFILE'     
    }

    if (this.username === 'PROFILE' && event.constructor.name !== 'LoginComponent') {
      let obs = this.global.GetUsernameByUUID().subscribe(
        res => {
          this.username = res.userName.toLocaleUpperCase();       
        }
      );
    }
  }



}
