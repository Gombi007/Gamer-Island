import { Component, HostListener, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { STRINGS } from 'src/app/strings.enum';
import { GameDetails } from '../../game-details.model';
import { Game } from '../../game.model';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;

  @Input()
  libraryGames: Game[] = []

  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;  
    
  }

  ngOnChanges(changes: SimpleChanges) {  
}

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  showLibrarayGames(){

  }

}

