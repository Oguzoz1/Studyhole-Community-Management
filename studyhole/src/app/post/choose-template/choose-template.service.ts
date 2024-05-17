import { Injectable } from '@angular/core';
import { CreatePostPayload } from '../create-post/create-post.payload';
@Injectable({
  providedIn: 'root'
})
export class ChooseTemplateService {
  
  currentPostPayload?: CreatePostPayload;

  constructor() { }


  getCurrentPayload(): CreatePostPayload {
    if (this.currentPostPayload) {
      return this.currentPostPayload;
    } else {
      throw new Error("THERE IS NO CURRENT PAYLOAD INBOUND.");
    }
  }

  setCurrentPayload(payload: CreatePostPayload){
    this.currentPostPayload = payload;
  }
}
