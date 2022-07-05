import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { STRINGS } from 'src/app/strings.enum';

@Component({
  selector: 'app-store-search',
  templateUrl: './store-search.component.html',
  styleUrls: ['./store-search.component.css']
})
export class StoreSearchComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  Search = new FormGroup(
 {
   searchGameInput: new FormControl("",[Validators.required, Validators.minLength(3)]),
   searchInNameOrDescription: new FormControl("name"), 
 }
  );

  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  getSearchData(){
    console.log(this.Search.get('searchInNameOrDescription')?.value+ " " + this.Search.get('searchGameInput')?.value);
  }


}
