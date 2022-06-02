import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticateService } from './authenticate.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthenticateService, private route: Router) { }

  canActivate() {
    if (this.authService.IsLoggedIn()) {
      return true;
    }
    this.route.navigate(["login"]);
    return false;
  }

}

