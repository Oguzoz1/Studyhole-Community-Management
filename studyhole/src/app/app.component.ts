import { Component, NgModule } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './header/header.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { LocalStorageService, NgxWebstorageModule } from 'ngx-webstorage';
import { HomeComponent } from './home/home.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    HeaderComponent,
    SignupComponent, 
    LoginComponent,
    HomeComponent,
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive
  ]
})

export class AppComponent {
  title = 'studyhole';
}
