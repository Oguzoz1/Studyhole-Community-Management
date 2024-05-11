import { Component, Input, OnInit } from '@angular/core';
import { ImageModel } from '../image-upload-service';
import { NgIf } from '@angular/common';
import { Observable, forkJoin } from 'rxjs';
import { CommunityService } from '../../community/community.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-image',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './image.component.html',
  styleUrl: './image.component.css'
})
export class ImageComponent implements OnInit{
  imageModel?: ImageModel;
  imageDataUrl?: string; // Base64 string representing the image data
  communityId: number;


  constructor(private comService: CommunityService,private activatedRoute: ActivatedRoute) {
    this.communityId = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    forkJoin({
      imageModel: this.getCommunityImage()
    }).subscribe(
      ({imageModel}) =>{
        this.imageDataUrl = 'data:image/jpeg;base64,' + imageModel?.imageData!;
      }
    )
  }

  getCommunityImage() : Observable<ImageModel>{
    return this.comService.getCommunityImage(this.communityId);
  }


}
