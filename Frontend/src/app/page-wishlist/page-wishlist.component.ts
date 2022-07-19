import { Component, HostListener, OnInit } from '@angular/core';
import { STRINGS } from '../strings.enum';

@Component({
  selector: 'app-page-wishlist',
  templateUrl: './page-wishlist.component.html',
  styleUrls: ['./page-wishlist.component.css']
})
export class PageWishlistComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;

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
