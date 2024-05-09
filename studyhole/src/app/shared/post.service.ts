import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostModel } from './post-model';
import { Observable } from 'rxjs';
import { CreatePostPayload } from '../post/create-post/create-post.payload';
import { environment } from '../environment';
// import { CreatePostPayload } from '../post/create-post/create-post.payload';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>(environment.baseUrl + '/api/posts/');
  }

  createPost(postPayload: CreatePostPayload): Observable<PostModel> {
    return this.http.post(environment.baseUrl + '/api/posts', postPayload);
  }

  getPost(postId: number): Observable<PostModel> {
    return this.http.get<PostModel>(environment.baseUrl + '/api/posts/' + postId);
  }

  getAllPostsByUser(name: string): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(environment.baseUrl + '/api/posts/user/' + name);
  }
  getAllPostsByCommunity(id: number): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(environment.baseUrl + '/api/posts/community/' + id);
  }
}