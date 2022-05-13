import { Component, HostListener, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
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
  screenshots: Screenshot[] = []
  firstScreenshot?: string;
  gameName?: string;

  constructor() { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  ngOnChanges(changes: SimpleChanges) {
    this.gameName = changes['game'].currentValue.name
    if (this.gameName !== '') {
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

  changePic(i: number) {
    this.firstScreenshot = this.screenshots[i].path_full;

  }

  nextPic() {
    var screenshotsLength = this.screenshots.length;

    var selectedPicId: any = this.screenshots.find(pic => pic.path_full === this.firstScreenshot)?.id
    if (Number(selectedPicId) !== NaN) {
      var id: number = selectedPicId;
      if (id >= 0 && id < screenshotsLength - 1) {
        id += 1;
        this.firstScreenshot = this.screenshots[id].path_full
        document.getElementById(String(id))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      } else {
        this.firstScreenshot = this.screenshots[0].path_full
        document.getElementById(String(0))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      }
    }
  }

  prevPic() {
    var screenshotsLength = this.screenshots.length;

    var selectedPicId: any = this.screenshots.find(pic => pic.path_full === this.firstScreenshot)?.id
    if (Number(selectedPicId) !== NaN) {
      var id: number = selectedPicId;
      if (id > 0 && id < screenshotsLength) {
        id -= 1;
        this.firstScreenshot = this.screenshots[id].path_full
        document.getElementById(String(id))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      } else {
        this.firstScreenshot = this.screenshots[screenshotsLength - 1].path_full
        document.getElementById(String(screenshotsLength - 1))?.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
      }
    }
  }

}

