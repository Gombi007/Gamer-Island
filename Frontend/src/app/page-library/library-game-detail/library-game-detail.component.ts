import { HttpClient } from '@angular/common/http';
import { Component, OnInit, SimpleChange, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { GameStat } from 'src/app/game-stat.model';
import { GlobalService } from 'src/app/global.service';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { STRINGS } from 'src/app/strings.enum';

@Component({
  selector: 'app-library-game-detail',
  templateUrl: './library-game-detail.component.html',
  styleUrls: ['./library-game-detail.component.css']
})
export class LibraryGameDetailComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  isPending = false;
  selectedGameAppId: any;
  actualGameStat: GameStat = new GameStat();

  constructor(private global: GlobalService, private http: HttpClient, private author: AuthorizationService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.getGameStatForThisUser$.subscribe();
    this.route.params.subscribe(steamAppIdChanges => {
      this.selectedGameAppId = steamAppIdChanges['steamAppid'];
      //@ts-ignore
      this.getGameStatForThisUser$.next();
    });
  }

  getGameStatForThisUser$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() => this.http.get(STRINGS.API_GET_GAME_STAT_BY_USER + this.global.getUUIDFromLocalStore() + '/' + this.selectedGameAppId, this.author.TokenForRequests())),
    tap((data: any) => {
      this.actualGameStat = data;   

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

}
