import { Component, HostListener, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { STRINGS } from 'src/app/strings.enum';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit, OnChanges {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  @Input() 
  gameStamAppid:number = 0;

  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }
  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes)
  }

    // update value when resize
    @HostListener('window:resize', ['$event'])
    onResize() {
      this.innerHeight = window.innerHeight - this.headerHeight;
    }
  

}
