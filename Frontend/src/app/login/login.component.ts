import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showLoginPage = true;
  samePasswords = true;
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

  constructor() {
    localStorage.clear();
  }

  ngOnInit(): void {
  }

  changeRegisterOrLogin() {
    this.showLoginPage = !this.showLoginPage;
  }

  StartLogin() {
    if (this.Login.valid) {
      console.log(this.Login.value)
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