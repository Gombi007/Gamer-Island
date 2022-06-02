import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { GameDetails } from './game-details.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Game Island';
 isLoggedIn = this.route.url === 'login';
 constructor(private route:Router){}

}

