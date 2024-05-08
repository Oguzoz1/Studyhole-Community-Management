import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { SignupRequestPayload } from '../signup/signup-request.payload';
import { LocalStorageService } from 'ngx-webstorage';
import { map, tap, throwError } from 'rxjs';
import { LoginResponse } from '../login/login-response.payload';
import { LoginRequestPayload } from '../login/login-request.payload';
import { environment } from '../../environment';
import { UserModel } from '../../user/user-model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();
  
  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  }
  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) { 

  }

  signup(signupRequestPayload: SignupRequestPayload){
    return this.httpClient.post(environment.baseUrl + '/api/auth/signup',
     signupRequestPayload, {responseType: 'text'})
  }

  login(loginRequestPayload: LoginRequestPayload){
    return this.httpClient.post<LoginResponse>(environment.baseUrl + '/api/auth/login',
    loginRequestPayload).pipe(map(data =>{
      this.localStorage.store('authToken', data.authToken);
      this.localStorage.store('username', data.username);
      this.localStorage.store('refreshToken', data.refreshToken);
      this.localStorage.store('expiresAt', data.expiresAt);

      this.loggedIn.emit(true);
      this.username.emit(data.username);
      return true;
    }));
  }

  logout() {
    this.httpClient.post(environment.baseUrl + '/api/auth/logout', this.refreshTokenPayload, { responseType: 'text' })
    .subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (error) => {
        console.error(error);
      }
    });
    this.localStorage.clear('authToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');
  }

  getJwtToken(){
    return this.localStorage.retrieve('authToken');
  }

  refreshToken(){
    return this.httpClient.post<LoginResponse>(environment.baseUrl + '/api/auth/refresh/token',this.refreshTokenPayload)
    .pipe(tap(response => {
      this.localStorage.clear('authToken');
      this.localStorage.clear('expiresAt');

      this.localStorage.store('authToken', response.authToken);
      this.localStorage.store('expiresAt', response.expiresAt);
    }));
  }


  getUserName() {
    return this.localStorage.retrieve('username');
  }
  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }
}
