import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  innerHeight!: number;
  headerHeight:number =57;
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
