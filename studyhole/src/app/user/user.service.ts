import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment';
import { AuthService } from '../auth/shared/auth.service';
import { UserModel } from './user-model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  subscribeCommunity(communityId: number): Observable<void>{
    return this.http.post<void>(environment.baseUrl + '/api/user/subscribe/' + communityId, null);
  }
  leaveCommunity(communityId: number): Observable<void>{
    return this.http.post<void>(environment.baseUrl + "/api/user/leave-community/" + communityId, null);
  }
  getCurrentUserPackage(){
    return this.http.get<UserModel>(environment.baseUrl + '/api/user/' + this.authService.getUserName())
  }
}
