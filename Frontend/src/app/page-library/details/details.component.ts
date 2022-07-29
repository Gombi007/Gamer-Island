import { HttpClient } from '@angular/common/http';
import { Component, HostListener, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { GlobalService } from 'src/app/global.service';
import { AuthenticateService } from 'src/app/login/service/authenticate.service';
import { AuthorizationService } from 'src/app/login/service/authorization.service';
import { STRINGS } from 'src/app/strings.enum';
import { GameDetails } from '../../game-details.model';
import { Game } from '../../game.model';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  isPending = false;

  @Input()
  libraryGames: Game[] = []

  constructor(private route:ActivatedRoute, private router:Router, private global:GlobalService, private http:HttpClient, private author:AuthorizationService){}

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;  
    this.librayGame$.subscribe();

    //@ts-ignore
    this.librayGame$.next();    
  }


  librayGame$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() => this.http.get(STRINGS.API_LIBRARY + this.global.getUUIDFromLocalStore(), this.author.TokenForRequests())),
    tap((data: any) => {
      this.libraryGames = data;    
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

  showGameDetails(selectedGameAppId: number) {  
    this.router.navigate([selectedGameAppId], {relativeTo:this.route})
  }

}

