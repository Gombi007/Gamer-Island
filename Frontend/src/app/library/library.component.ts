import { Component, HostListener, OnInit } from '@angular/core';
import { Game } from '../game.model';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {
  
  innerHeight!: number;
  headerHeight:number =155;
  games: Game[] = [
    { "id": 1, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1091500/header.jpg", "title": "Cyberpunk 2077" },
    { "id": 2, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1551360/header.jpg", "title": "Forza Horizon 5" },
    { "id": 3, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/534380/capsule_sm_120.jpg", "title": "Dying Light 2 Stay Human" },
    { "id": 4, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/header.jpg", "title": "Apex Legends™" },
    { "id": 5, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg", "title": "Grand Theft Auto V" },
    { "id": 6, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172620/header.jpg", "title": "Sea of Thieves" },
    { "id": 7, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172380/header.jpg", "title": "STAR WARS Jedi: Fallen Order™" },
    { "id": 1, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1091500/header.jpg", "title": "Cyberpunk 2077" },
    { "id": 2, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1551360/header.jpg", "title": "Forza Horizon 5" },
    { "id": 3, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/534380/capsule_sm_120.jpg", "title": "Dying Light 2 Stay Human" },
    { "id": 4, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/header.jpg", "title": "Apex Legends™" },
    { "id": 5, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg", "title": "Grand Theft Auto V" },
    { "id": 6, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172620/header.jpg", "title": "Sea of Thieves" },
    { "id": 7, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172380/header.jpg", "title": "STAR WARS Jedi: Fallen Order™" },
    { "id": 8, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1091500/header.jpg", "title": "Cyberpunk 2077" },
    { "id": 9, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1551360/header.jpg", "title": "Forza Horizon 5" },
    { "id": 10, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/534380/capsule_sm_120.jpg", "title": "Dying Light 2 Stay Human" },
    { "id": 11, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/header.jpg", "title": "Apex Legends™" },
    { "id": 12, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg", "title": "Grand Theft Auto V" },
    { "id": 13, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172620/header.jpg", "title": "Sea of Thieves" },
    { "id": 14, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172380/header.jpg", "title": "STAR WARS Jedi: Fallen Order™" },
    { "id": 15, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1091500/header.jpg", "title": "Cyberpunk 2077" },
    { "id": 16, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1551360/header.jpg", "title": "Forza Horizon 5" },
    { "id": 17, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/534380/capsule_sm_120.jpg", "title": "Dying Light 2 Stay Human" },
    { "id": 18, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/header.jpg", "title": "Apex Legends™" },
    { "id": 19, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg", "title": "Grand Theft Auto V" },
    { "id": 20, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172620/header.jpg", "title": "Sea of Thieves" },
    { "id": 21, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172380/header.jpg", "title": "STAR WARS Jedi: Fallen Order™" },
    { "id": 22, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/534380/capsule_sm_120.jpg", "title": "Dying Light 2 Stay Human" },
    { "id": 23, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/header.jpg", "title": "Apex Legends™" },
    { "id": 24, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg", "title": "Grand Theft Auto V" },
    { "id": 25, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172620/header.jpg", "title": "Sea of Thieves" },
    { "id": 26, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172380/header.jpg", "title": "STAR WARS Jedi: Fallen Order™" },
    { "id": 27, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1091500/header.jpg", "title": "Cyberpunk 2077" },
    { "id": 28, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1551360/header.jpg", "title": "Forza Horizon 5" },
    { "id": 29, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/534380/capsule_sm_120.jpg", "title": "Dying Light 2 Stay Human" },
    { "id": 30, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/header.jpg", "title": "Apex Legends™" },
    { "id": 31, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg", "title": "Grand Theft Auto V" },
    { "id": 32, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172620/header.jpg", "title": "Sea of Thieves" },
    { "id": 33, "img": "https://cdn.cloudflare.steamstatic.com/steam/apps/1172380/header.jpg", "title": "STAR WARS Jedi: Fallen Order™" },
  ]

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
