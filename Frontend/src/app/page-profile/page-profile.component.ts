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
  user: { avatar: string, balance: number, email: string, userName: string, userUUID: string } =
    { 'avatar': '', 'balance': 0.0, 'email': '', 'userName': '', 'userUUID': '' };

  usernameInput = new FormControl({ value: 'username', disabled: true }, Validators.required);
  avatarInput = new FormControl('avatarURL', [Validators.required, Validators.pattern('^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$')]);
  emailInput = new FormControl('email', [Validators.required, Validators.email]);
  isInvalid = false;
  invalidMessage = '';
  wasUpdateSuccessfully = false;

  constructor(private http: HttpClient, private global: GlobalService, private route: Router, private author: AuthorizationService) { }

  ngOnInit(): void {
    this.innerHeight = window.innerHeight - this.headerHeight;
    this.getUserInformationByUUID$.subscribe();
    this.updateUserData$.subscribe();
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
      this.usernameInput.setValue(this.user.userName);
      this.avatarInput.setValue(this.user.avatar);
      this.emailInput.setValue(this.user.email);
      this.isPending = false;
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

  updateUserData$ = new Subject().pipe(
    tap(() => {
      this.isPending = true;
    }),
    switchMap(() =>
      this.http.post(STRINGS.API_USER_UPDATE_USER_DATA, this.user, this.author.TokenForRequests())),
    tap((data: any) => {      
      this.isPending = false;
      this.wasUpdateSuccessfully = true;
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

  saveNewUserData() {
    this.isInvalid = false;
    this.wasUpdateSuccessfully = false;

    if (this.avatarInput.valid && this.emailInput.valid) {
      this.user.avatar = this.avatarInput.value;
      this.user.email = this.emailInput.value;
      //@ts-ignore
      this.updateUserData$.next();

    } else {
      if (this.avatarInput.invalid) {
        this.isInvalid = true;
        this.invalidMessage = 'Not valid avatar URL'
      }
      if (this.emailInput.invalid) {
        this.isInvalid = true
        this.invalidMessage = 'Not valid email address'
      }
    }
  }

  balanceTopup() {

  }

}
