import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnChanges, OnInit, SimpleChange, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { GlobalService } from '../global.service';
import { AuthGuard } from '../login/service/auth.guard';
import { AuthenticateService } from '../login/service/authenticate.service';
import { STRINGS } from '../strings.enum';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input()
  userObject = { username: '', balance: '', avatar: '' };

  constructor(private auth: AuthenticateService, private global: GlobalService) { }

  ngOnInit(): void {
  }

  isUserLoggedIn() {
    return this.auth.IsLoggedIn();
  }

  get cartCounter(): number {
    this.global.isThereAnyItemInTheCart();
    return this.global.cartItemsCounter;

  }



}


