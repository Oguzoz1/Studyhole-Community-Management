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
  
  constructor(private comService: CommunityService, private activatedRoute: ActivatedRoute
    , private postService: PostService, private router: Router, private chooseService: ChooseTemplateService) {
      this.communityId = this.activatedRoute.snapshot.params['id'];
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
      )
      this.chooseForm = new FormGroup({
        title: new FormControl('', Validators.required),
        templates: new FormControl('', Validators.required)
      })
    }
    
    navigateToCreatePost(communityId: number) {
      const selectedTemplateId = this.chooseForm?.get('templates')?.value;
      const selectedTemplate = this.postTemplate?.find(template => template.id == selectedTemplateId);
      const title = this.chooseForm?.get('title')?.value;
      if (selectedTemplate && title) {
        const newPost: CreatePostPayload = {
          postTitle: title,
          postTemplate: selectedTemplate
        };
        this.chooseService.setCurrentPayload(newPost);
        this.router.navigateByUrl('/create-post/' + communityId);
      }
    }

}
