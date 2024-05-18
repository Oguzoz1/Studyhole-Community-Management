import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideToastr } from 'ngx-toastr';
import { NgxWebstorageModule, LocalStorageService } from 'ngx-webstorage';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { tokenInterceptor } from './token.interceptor';
import { errorInterceptor } from './error.interceptor';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    provideHttpClient(withInterceptors([
      tokenInterceptor,
      errorInterceptor
    ])),
    provideToastr(),
    importProvidersFrom(BrowserAnimationsModule),
    importProvidersFrom(NgxWebstorageModule.forRoot()),
    importProvidersFrom(FontAwesomeModule), provideAnimationsAsync(),
  ]
};
