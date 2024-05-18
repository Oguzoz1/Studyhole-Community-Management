import { Component, OnInit } from '@angular/core';
import { CommunityModel } from '../../community/community-model';
import { forkJoin } from 'rxjs';
import { CommunityService } from '../../community/community.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../post.service';
import { PostTemplateModel } from '../post-template-model';
import { NgFor, NgIf } from '@angular/common';
import { HeaderComponent } from "../../header/header.component";
import { CreatePostPayload } from '../create-post/create-post.payload';
import { ChooseTemplateService } from './choose-template.service';

@Component({
  selector: 'app-choose-template',
  standalone: true,
  templateUrl: './choose-template.component.html',
  styleUrl: './choose-template.component.css',
  imports: [
    NgFor,
    NgIf,
    ReactiveFormsModule,
    HeaderComponent
  ]
})
export class ChooseTemplateComponent implements OnInit {
  communityId: number;
  community?: CommunityModel;
  chooseForm?: FormGroup;
  postTemplate?: PostTemplateModel[];
  postPayload: CreatePostPayload;

  constructor(private comService: CommunityService, private activatedRoute: ActivatedRoute
    , private postService: PostService, private router: Router, private chooseService: ChooseTemplateService) {
      this.communityId = this.activatedRoute.snapshot.params['id'];

      this.postPayload = {
        postTitle: '',
        description: '',
        communityId: -1
      }
    }
    
    ngOnInit(): void {
      forkJoin({
        community: this.comService.getCommunityById(this.communityId),
        postTemplate: this.postService.getAllPostTemplatebyCommunityId(this.communityId)
      }).subscribe(
        ({community, postTemplate}) => {
          this.community = community;
          this.postTemplate = postTemplate;
        }
      );
      this.chooseForm = new FormGroup({
        title: new FormControl('', Validators.required),
        templates: new FormControl(''),
        description: new FormControl('')
      });
    }
    
    navigateToCreatePost(communityId: number) {
      const selectedTemplateId = this.chooseForm?.get('templates')?.value;
      const selectedTemplate = this.postTemplate?.find(template => template.id == selectedTemplateId);
      const title = this.chooseForm?.get('title')?.value;
      const description = this.chooseForm?.get('description')?.value;
      if (selectedTemplate && title) {
        const newPost: CreatePostPayload = {
          postTitle: title,
          postTemplate: selectedTemplate,
          description: description
        };
        this.chooseService.setCurrentPayload(newPost);
        this.router.navigateByUrl('/create-post/' + communityId);
      }
    }
    
    onTemplateChange() {
      if (!this.chooseForm?.get('templates')?.value) {
        this.chooseForm?.get('description')?.reset();
      }
    }
    
    isDescriptionVisible(): boolean {
      return this.chooseForm?.get('templates')?.value === '';
    }

    createPost() {
      this.postPayload.postTitle = this.chooseForm?.get('title')?.value;
      this.postPayload.communityId = this.communityId;
      this.postPayload.description = this.chooseForm?.get('description')?.value;

      this.postService.createPost(this.postPayload, this.communityId).subscribe(
        {
          next: (data) => {
          this.router.navigateByUrl('/view-post/' + data.postId);
        }, error: (error) => {
          console.error(error);
        }
      })
    }
  }
  