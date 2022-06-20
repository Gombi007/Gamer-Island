import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = 46;

  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

    // update value when resize
    @HostListener('window:resize', ['$event'])
    onResize() {
      this.innerHeight = window.innerHeight - this.headerHeight;
    }
  

}
