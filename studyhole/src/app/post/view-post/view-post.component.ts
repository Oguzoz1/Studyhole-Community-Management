import { Component, OnInit, contentChild } from '@angular/core';
import { PostModel } from '../post-model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentPayload } from '../../comment/comment.payload';
import { CommentService } from '../../comment/comment.service';
import { CommunitySideBarComponent } from '../../community/community-side-bar/community-side-bar.component';
import { SideBarComponent } from '../../shared/side-bar/side-bar.component';
import { VoteButtonComponent } from '../../shared/vote-button/vote-button.component';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { HeaderComponent } from '../../header/header.component';
import { CommunityService } from '../../community/community.service';
import { CommunityModel } from '../../community/community-model';
import { DataField, DateSField, PostTemplateModel, TextField } from '../post-template-model';

@Component({
    selector: 'app-view-post',
    standalone: true,
    templateUrl: './view-post.component.html',
    styleUrl: './view-post.component.css',
    imports: [
        CommunitySideBarComponent,
        ReactiveFormsModule,
        VoteButtonComponent,
        NgFor,
        HeaderComponent,
        SideBarComponent,
        NgIf,
        DatePipe
    ]
})
export class ViewPostComponent implements OnInit {
  
  postId: number;
  post?: PostModel;
  postTemplate?: PostTemplateModel;
  commentForm: FormGroup;
  commentPayload?: CommentPayload;
  comments?: CommentPayload[];
  
  constructor(private postService: PostService, private activateRoute: ActivatedRoute,
    private commentService: CommentService, private router: Router, private comServ: CommunityService) {
      this.postId = this.activateRoute.snapshot.params['id'];
      
      this.commentForm = new FormGroup({
        text: new FormControl('', Validators.required)
      });
      this.commentPayload = {
        text: '',
        postId: this.postId
      };
    }
    
    ngOnInit(): void {
      this.getPostById();
    }

    goToCommunity(name: string): void {
      this.comServ.getCommunityByName(name).subscribe((com: CommunityModel) => {
        console.log(com.communityId);
        com.ownerUsers?.forEach(user => {
          console.log(user.username);
          this.router.navigateByUrl('view-community/' + com.communityId);
        });
    
      });
    }
  postComment() {
    this.commentPayload!.text = this.commentForm?.get('text')?.value;

    this.commentService.postComment(this.commentPayload!).subscribe(
      {
        next: () => {
          this.commentForm?.get('text')?.setValue('');
          this.getCommentsForPost(this.postId);
        }, error: () =>{
          console.error("Error during commenting");
        }
      }
    )
  }
  getField(field: DataField): string {
    switch (field.type) {
      case 'TextField':
        const textField = field as TextField;
        console.log(textField.input);
        return textField.input!;
      case 'DateSField':
        const dateField = field as DateSField;
        return dateField.input?.toString()!;
      case 'UrlField':
        return 'UrlField content';
      case 'ImageField':
        return 'ImageField content'; 
      default:
        return '';
    }
  }
  private getPostById() {
    this.postService.getPost(this.postId).subscribe(
      {
        next: (data) => {
          this.post = data;
          this.getCommentsForPost(this.post.postId!);
        }, error: (error) => {
          console.error(error)
        }
      }
    )
  }

  private getCommentsForPost(id:number) {
    this.commentService.getAllCommentsForPost(id).subscribe(
      {
        next: (data) => {
          this.comments = data;
        }, error: (error) => {
          console.error(error)
        }
      }
    )
  }

}