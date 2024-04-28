import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './shared/auth.service';
import { inject } from '@angular/core';

export const canActivate: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService); // Replace this with actual injection in your Angular app
  const router = inject(Router);
  const isAuthenticated = authService.isLoggedIn();

  if (isAuthenticated) {
    return true;
  } else {
    router.navigateByUrl('/login');
    return false;
  }
};
