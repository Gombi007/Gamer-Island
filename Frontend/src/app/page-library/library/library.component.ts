import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Game } from '../../game.model';
import { HttpClient } from '@angular/common/http';
import { catchError, EMPTY, fromEvent, map, of, Subject, switchMap, tap } from 'rxjs';
import { GameDetails } from '../../game-details.model';
import { STRINGS } from 'src/app/strings.enum';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { ActivatedRoute, Router } from '@angular/router';
import { GlobalService } from 'src/app/global.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {

  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_LEFT_SIDE;
  games: Game[] = [];
  gamesClone: Game[] = [];
  isPending: Boolean = false;
  userUUID = '';

  constructor(private http: HttpClient, private author: AuthorizationService, private router: Router, private global: GlobalService,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.userUUID = this.global.getUUIDFromLocalStore() || '';
    this.librayGame$.subscribe();

    //@ts-ignore
    this.librayGame$.next();
    this.router.navigate(["games"], {relativeTo:this.route})
  }

  librayGame$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() => this.http.get(STRINGS.API_LIBRARY + this.userUUID, this.author.TokenForRequests())),
    tap((data: any) => {
      this.isPending = false;
      this.games = data;
      this.gamesClone = this.games;  
    }),
    catchError(error => {
      let message = error.error.error_message;
      if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.router.navigate(['login']);
      }
      return EMPTY;
    })
  );


  // update value when resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.innerHeight = window.innerHeight - this.headerHeight;
  }

  searching(searching: string) {
    this.gamesClone = this.games;
    let filteredResult = this.gamesClone.filter(game => {
      return game.name.toLowerCase().includes(searching);
    });

    this.gamesClone = filteredResult;
  }
  
  navigateToGameDetail(steamAppid: number) {
    this.router.navigate(["games/"+steamAppid], {relativeTo:this.route})
  }


}
