import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() { }

  showLoginPage = true;
  ngOnInit(): void {
  }

  changeRegisterOrLogin(){
    this.showLoginPage = !this.showLoginPage;
  }

}
