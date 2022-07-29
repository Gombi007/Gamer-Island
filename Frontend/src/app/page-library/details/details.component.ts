import { Component, HostListener, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
  emptyGames: boolean = true;
  isMainDetailsView: boolean = true;
  selectedGameAppId: number = 0;

  constructor(private router:ActivatedRoute) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;      
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.libraryGames.length > 0) {
      this.emptyGames = false;
    }
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  showGameDetails(selectedGameAppId: number) {
    this.selectedGameAppId = selectedGameAppId;
    this.isMainDetailsView = !this.isMainDetailsView;  

  }

  getGameStat() {
    this.isMainDetailsView = !this.isMainDetailsView;
    let game = this.libraryGames.filter((game) =>{ return game.appId === this.selectedGameAppId});    

  }

}

