import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY, Subject, switchMap, tap } from 'rxjs';
import { GlobalService } from '../global.service';
import { AuthorizationService } from '../login/service/authorization.service';
import { STRINGS } from '../strings.enum';

@Component({
  selector: 'app-page-profile',
  templateUrl: './page-profile.component.html',
  styleUrls: ['./page-profile.component.css']
})
export class PageProfileComponent implements OnInit {
  innerHeight!: number;
  headerHeight: number = STRINGS.HEADER_HEIGHT_FOR_CONTENT;
  isPending = false;
  user:{avatar:string,balance:number,email:string,userName:string,userUUID:string}=
   {'avatar':'','balance':0.0,'email':'','userName':'','userUUID':''};

  userData = new FormGroup(
    {
      avatarURL: new FormControl("", Validators.required),
      username: new FormControl("", Validators.required),
      email: new FormControl("", Validators.required),
    }
  );

  constructor(private http:HttpClient, private global:GlobalService, private route:Router, private author:AuthorizationService) { }

  ngOnInit(): void {  
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.getUserInformationByUUID$.subscribe();
    //@ts-ignore
    this.getUserInformationByUUID$.next();
  }

  getUserInformationByUUID$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.get(STRINGS.API_USER_GET_USER_DATA + this.global.getUUIDFromLocalStore(), this.author.TokenForRequests())),
    tap((data: any) => {
      this.user = data;
      console.log(this.user)
   
    }),
    catchError(error => {
      let message = error.error.error_message;
      if (message.includes("Token has expired")) {
        this.global.experiedSession = true;
        this.route.navigate(['login']);
      }
      return EMPTY;
    })
  );

    // update value when resize
    @HostListener('window:resize', ['$event'])
    onResize() {
      this.innerHeight = window.innerHeight - this.headerHeight;
    }

    saveNewUserData(){
      console.log(this.userData.value);
    }

    balanceTopup(){

    }

}
