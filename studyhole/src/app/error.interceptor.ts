import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error) => {
      if([401, 403].includes(error.status)){
        console.error('Unauthorized Request')
      }
      const e = error.error.message || error.statusText;
      console.error(e);
      return throwError(() => error);
    })
  )
};
