import { Component, HostListener, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { GameDetails } from '../game-details.model';

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
  screenshots:string[] = []
  firstScreenshot: string="";
  gameName?: string;

  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  ngOnChanges(changes: SimpleChanges) {
    this.gameName = changes['game'].currentValue.name
    if (this.gameName !== 'TEST NAME') {
      this.isSelectedGame = true;
      this.screenshots = changes['game'].currentValue.screenshot_urls;

      if (this.screenshots.length != 0) {
        this.firstScreenshot = this.screenshots[0];
      } else {
        this.isSelectedGame = false;
      }
    }
  }

  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  changePic(i: number) {
    this.firstScreenshot = this.screenshots[i];

  }

  nextPic() {
    var screenshotsLength = this.screenshots.length;
    var selectedPicId = this.screenshots.indexOf(this.firstScreenshot);    

    if (selectedPicId !== undefined) {
      var id: number = selectedPicId;
      if (id >= 0 && id < screenshotsLength - 1) {
        id += 1;
        this.firstScreenshot = this.screenshots[id]
        document.getElementById(String(id))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      } else {
        this.firstScreenshot = this.screenshots[0]
        document.getElementById(String(0))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      }
    }
  }

  prevPic() {
    var screenshotsLength = this.screenshots.length;
    var selectedPicId = this.screenshots.indexOf(this.firstScreenshot);    

    if (selectedPicId !== undefined) {
      var id: number = selectedPicId;
      if (id > 0 && id < screenshotsLength) {
        id -= 1;
        this.firstScreenshot = this.screenshots[id];
        document.getElementById(String(id))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      } else {
        this.firstScreenshot = this.screenshots[screenshotsLength - 1];
        document.getElementById(String(screenshotsLength - 1))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      }
    }
  }

}

