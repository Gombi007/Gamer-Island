import { Component, OnInit } from '@angular/core';
import { GameDetails } from '../game-details.model';

@Component({
  selector: 'app-page-library',
  templateUrl: './page-library.component.html',
  styleUrls: ['./page-library.component.css']
})
export class PageLibraryComponent implements OnInit {

  game = new GameDetails();

  constructor() { }
  ngOnInit(): void { }

  getGame(gameFromLibrary: any) {
    this.game = gameFromLibrary;

  }

}
