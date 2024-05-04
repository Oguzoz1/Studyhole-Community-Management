import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { VotePayload } from './vote-button/vote-payload';
import { Observable } from 'rxjs';
import { environment } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private http: HttpClient) { }

  votePost(votePayload: VotePayload): Observable<any> {
    return this.http.post(environment.baseUrl + '/api/votes/post', votePayload);
  }
}