import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-library-search',
  templateUrl: './library-search.component.html',
  styleUrls: ['./library-search.component.css']
})
export class LibrarySearchComponent implements OnInit {

  @Output()
  gameSearchInput = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  getSearchInputData(event: any) {
    let pressedKey = event.target.value;
    let gameSearchInputText = "";

    if (event.key === "Enter") {
      event.preventDefault();
    }
    gameSearchInputText = pressedKey;  

    this.gameSearchInput.emit(gameSearchInputText);

  }

}
