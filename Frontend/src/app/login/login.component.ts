import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticateService } from './service/authenticate.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  showLoginPage = true;
  samePasswords = true;
  responseData: any;
  errorResponseData: any;
  Login = new FormGroup(
    {
      userName: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required)
    }
  );
  Register = new FormGroup(
    {
      userName: new FormControl("", Validators.required),
      email: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required),
      rePassword: new FormControl("", Validators.required),
    }
  );

  constructor(private service: AuthenticateService, private route: Router) {
    localStorage.clear();
  }

  ngOnInit(): void {
  }

  changeRegisterOrLogin() {
    this.showLoginPage = !this.showLoginPage;
  }

  StartLogin() {
    if (this.Login.valid) {
      this.service.LoginViaBackend(this.Login.value).subscribe({
        next: (value) => {
          if (value !== null) {
            this.responseData = value;
            localStorage.setItem('user_id', this.responseData.user_id);
            localStorage.setItem('token', this.responseData.token);
            this.route.navigate(['']);
            this.service.GetLoggedUserName().subscribe({
              // TODO username showing in the header profile menu
              next:(response)=>{               
                console.log(response)
              }
            });
          }
        },
        error: (error) => {
          this.errorResponseData = error;
          alert(this.errorResponseData.error);
        }
      });

    }
  }

  StartRegister() {
    if (this.Register.valid) {
      if (this.Register.get("password")?.value !== this.Register.get("rePassword")?.value) {
        this.samePasswords = false;
      } else {
        this.samePasswords = true;
      }

    }

  }

}