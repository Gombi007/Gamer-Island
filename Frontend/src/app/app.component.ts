import { Component } from '@angular/core';
import { GameDetails } from './game-details.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Game Island';
  game = new GameDetails();

  getGame(gameFromLibrary:any){
    console.log(gameFromLibrary);
    this.game = gameFromLibrary;

  }
}
