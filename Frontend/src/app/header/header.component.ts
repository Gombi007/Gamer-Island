import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthGuard } from '../login/service/auth.guard';
import { AuthenticateService } from '../login/service/authenticate.service';
import { STRINGS } from '../strings.enum';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName = "PROFILE";


  constructor(private auth: AuthenticateService) { }

  ngOnInit(): void {
  }

  isUserLoggedIn() {  
       return this.auth.IsLoggedIn();
    
  }

}


