import { Component, HostListener, Input, OnInit } from '@angular/core';
import { GameDetails } from '../game-details.model';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  innerHeight!: number;
  headerHeight:number =57;

  @Input()
  game:GameDetails = new GameDetails()

  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight-this.headerHeight;   
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight-this.headerHeight;
  }

}
