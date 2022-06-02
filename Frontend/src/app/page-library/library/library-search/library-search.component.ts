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

    gameSearchInputText = pressedKey;
    this.gameSearchInput.emit(gameSearchInputText);

  }

  /*todo regx only letter and numner and backspace and space
  const input = String.fromCharCode(event.keyCode);
  if (/[a-zA-Z0-9-_ ]/.test(input)) {  alert('input was a letter, number, hyphen, underscore or space');}  
  */

}
