import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GlobalService } from '../global.service';
import { AuthenticateService } from './service/authenticate.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showLoginPage = true;
  regValidationError = false;
  regInfoMessageBox = false
  loginSessionHasExperied = false
  loginError = false
  errorMessage: any
  successRegistrationMessage = ''
  responseData: any;
  Login = new FormGroup(
    {
      userName: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required)
    }
  );
  Register = new FormGroup(
    {
      userName: new FormControl("", [Validators.required, Validators.minLength(6)]),
      email: new FormControl("", [Validators.required, Validators.email]),
      password: new FormControl("", [Validators.required, Validators.minLength(6)]),
      rePassword: new FormControl("", [Validators.required, Validators.minLength(6)]),
    }
  );

  constructor(private service: AuthenticateService, private route: Router, private global: GlobalService) {
    localStorage.clear();
  }

  ngOnInit(): void {
    this.loginSessionHasExperied = this.global.experiedSession;
  }

  changeRegisterOrLogin() {
    this.showLoginPage = !this.showLoginPage;
  }

  StartLogin() {
    this.loginSessionHasExperied = false
    this.regValidationError = false;
    this.regInfoMessageBox = false;

    if (this.Login.valid) {

      this.service.LoginViaBackend(this.Login.value).subscribe({
        next: (value) => {
          if (value !== null) {
            this.responseData = value;
            localStorage.setItem('user_id', this.responseData.user_id);
            localStorage.setItem('token', this.responseData.token);                              
            this.route.navigate(['']);
           
          }
        },
        error: (error) => {
          this.loginError = true;
          this.errorMessage = error.error.message;

        }
      });

    }
  }

  //convenience getter for easy access to form fields
  get reg(): { [key: string]: AbstractControl; } {
    return this.Register.controls;
  }

  
  StartRegister() {
    this.loginSessionHasExperied = false;
    this.regValidationError = false;
    this.loginError = false;

    if (this.Register.valid) {
      this.service.RegisterViaBackend(this.Register.value).subscribe({
        next: (data) => {
          this.regInfoMessageBox = true;
          this.showLoginPage = true
        },
        error: (err) => {
          this.regValidationError = true;
          this.errorMessage = err.error;
        }
      });
    }
  }
}