import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {
  

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot): boolean {
    
    if(this.authService.isAuthenticated()){
      return true;
    }
    
    // No autenticado
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false

  }

};
