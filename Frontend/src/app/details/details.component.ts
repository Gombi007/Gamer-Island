import { Component, HostListener, Input, OnInit, OnChanges, SimpleChanges} from '@angular/core';
import { GameDetails, Screenshot } from '../game-details.model';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = 57;

  @Input()
  game: GameDetails = new GameDetails()
  isSelectedGame: boolean = false
  screenshots:Screenshot[] = []
  firstScreenshot:any;


  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  ngOnChanges(changes: SimpleChanges) {    
    var nameCahnged =changes['game'].currentValue.name
    if (nameCahnged !== '') {
      this.isSelectedGame = true;
      this.screenshots = changes['game'].currentValue.screenshots;
     this.firstScreenshot = this.screenshots.find(image => image.id == 0)?.path_full;
    } else {
      this.isSelectedGame = false;
    }
  }


  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }


}

