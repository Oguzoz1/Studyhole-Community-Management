import { Component, Input } from '@angular/core';
import { ImageUploadService } from '../image-upload-service';
import { CommunityModel } from '../../community/community-model';

@Component({
  selector: 'app-image-upload-component',
  standalone: true,
  imports: [],
  templateUrl: './image-upload-component.component.html',
  styleUrl: './image-upload-component.component.css'
})
export class ImageUploadComponentComponent {
  @Input() communityModel?: CommunityModel;
  selectedFile: File | undefined;

  constructor(private imageUploadService: ImageUploadService) { }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadFile() {
 if (this.selectedFile && this.communityModel && this.communityModel.communityId) {
    this.imageUploadService.communityUpload(this.selectedFile, this.communityModel.communityId)
      .subscribe(
        () => console.log("Upload successful"),
        error => console.error("Upload failed:", error)
      );
  }
  }
}
