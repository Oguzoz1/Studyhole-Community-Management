import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class ImageUploadService {
  constructor(private http: HttpClient) {}

  communityUpload(file: File, id: number) {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(`${environment.baseUrl}/api/community/upload-image/${id}`, formData);
  }

}

export class ImageModel{
  id?: number;
  name?: string;
  type?: string;
  imageData?: Uint8Array;
}