import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostModel } from './post-model';
import { Observable } from 'rxjs';
import { CreatePostPayload } from './create-post/create-post.payload';
import { environment } from '../environment';
import { DataField, PostTemplateModel } from './post-template-model';
// import { CreatePostPayload } from '../post/create-post/create-post.payload';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>(environment.baseUrl + '/api/posts/');
  }

  createPost(postPayload: CreatePostPayload, id: number): Observable<PostModel> {
    return this.http.post(environment.baseUrl + '/api/posts/create-post/' + id, postPayload);
  }

  createPostTemplate(postTemplate: PostTemplateModel, id: number) : Observable<PostTemplateModel>{
    console.log(postTemplate.dataFields![0]);
    return this.http.post<PostTemplateModel>(environment.baseUrl + '/api/posts/template/' + id, postTemplate);
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
  getAllPostTemplatebyCommunityId(id: number): Observable<PostTemplateModel[]>{
    console.log("SENDING API");
    return this.http.get<PostTemplateModel[]>(environment.baseUrl + '/api/posts/template-by-community/' + id);
  }
  getPostTemplatebyId(id: number): Observable<PostTemplateModel>{
    return this.http.get<PostTemplateModel>(environment.baseUrl + '/api/posts/template/' + id);
  }
  getPostContentbyPostId(id: number): Observable<DataField[]>{
    return this.http.get<DataField[]>(environment.baseUrl + '/api/posts/content/' + id);
  }
}