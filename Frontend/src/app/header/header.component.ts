import { Component, OnInit } from '@angular/core';
import { AuthGuard } from '../login/service/auth.guard';
import { AuthenticateService } from '../login/service/authenticate.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private auth:AuthenticateService) { }

  ngOnInit(): void {
  }

  isUserLoggedIn(){
   return this.auth.IsLoggedIn();
  }

}
