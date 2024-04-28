import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth/shared/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const authToken = authService.getJwtToken();

  if (req.url.indexOf('refresh') !== -1 || req.url.indexOf('login') !== -1) {
    return next(req);
  }

  const authReq = req.clone({
    headers: req.headers.set('Authorization', 'Bearer ' + authToken)
  })

  return next(authReq);
};
