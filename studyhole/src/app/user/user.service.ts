import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  subscribeCommunity(communityId: number): Observable<void>{
    return this.http.post<void>(environment.baseUrl + '/api/user/subscribe/' + communityId, null);
  }
}
