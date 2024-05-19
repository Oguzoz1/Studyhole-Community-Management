import { Component, Input } from '@angular/core';
import { ImageModel } from '../../shared/image-upload-service';
import { PostService } from '../../post/post.service';
import { Observable, forkJoin } from 'rxjs';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-image-field',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './image-field.component.html',
  styleUrl: './image-field.component.css'
})
export class ImageFieldComponent {
  @Input() imageId?: number;
  imageModel?: ImageModel;
  imageDataUrl?: string; // Base64 string representing the image data


  constructor(private postService: PostService) {
    
  }

  ngOnInit(): void {
    forkJoin({
      imageModel: this.getDatafieldImagebyImageId()
    }).subscribe(
      ({imageModel}) =>{
        this.imageDataUrl = 'data:image/jpeg;base64,' + imageModel?.imageData!;
      }
    )
  }

  getDatafieldImagebyImageId() : Observable<ImageModel>{
    return this.postService.getDatafieldImagebyImageId(this.imageId!);
  }


}
