import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreatePostPayload } from './create-post.payload';
import { CommunityModel } from '../../community/community-model';
import { Router } from '@angular/router';
import { PostService } from '../post.service';
import { CommunityService } from '../../community/community.service';
import { HeaderComponent } from '../../header/header.component';
import { PostTemplateComponent } from "../post-template/post-template.component";


@Component({
    selector: 'app-create-post',
    standalone: true,
    templateUrl: './create-post.component.html',
    styleUrl: './create-post.component.css',
    imports: [
        ReactiveFormsModule,
        CommonModule,
        HeaderComponent,
    ]
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup = new FormGroup({})
  postPayload: CreatePostPayload;
  communities?: Array<CommunityModel>;

  constructor(private router: Router, private postService: PostService,
    private communityService: CommunityService) {
    this.postPayload = {
      postTitle: '',
      description: '',
      communityName: ''
    }
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postTitle: new FormControl('', Validators.required),
      communityName: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.communityService.getAllJoinedCommunities().subscribe({
      next: (data) => {
        this.communities = data;
      }, error: (error) => {
        console.error(error);
      }
    })
  }

  createPost() {
    this.postPayload.postTitle = this.createPostForm?.get('postTitle')?.value;
    this.postPayload.communityName = this.createPostForm?.get('communityName')?.value;
    this.postPayload.description = this.createPostForm?.get('description')?.value;

    this.postService.createPost(this.postPayload).subscribe(
      {
        next: (data) => {
        this.router.navigateByUrl('/view-post/' + data.postId);
      }, error: (error) => {
        console.error(error);
      }
    })
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }

}