import { Component, OnInit } from '@angular/core';
import { PostModel } from '../../shared/post-model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../../shared/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentPayload } from '../../comment/comment.payload';
import { CommentService } from '../../comment/comment.service';
import { CommunitySideBarComponent } from '../../community/community-side-bar/community-side-bar.component';
import { SideBarComponent } from '../../shared/side-bar/side-bar.component';
import { VoteButtonComponent } from '../../shared/vote-button/vote-button.component';
import { NgFor } from '@angular/common';
import { HeaderComponent } from '../../header/header.component';
import { CommunityService } from '../../community/community.service';
import { CommunityModel } from '../../community/community-model';

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
        SideBarComponent
    ]
})
export class ViewPostComponent implements OnInit {
  
  postId: number;
  post?: PostModel;
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
      this.getCommentsForPost();
    }

    goToCommunity(name: string): void {
      this.comServ.getCommunityByName(name).subscribe((com: CommunityModel) => {
        console.log(com.communityId);
        // Handle Hibernate proxies
        com.ownerUsers?.forEach(user => {
          // Access user properties to force initialization
          console.log(user.username);
          // Add additional handling as needed
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
          this.getCommentsForPost();
        }, error: () =>{
          console.error("Error during commenting");
        }
      }
    )
  }

  private getPostById() {
    this.postService.getPost(this.postId).subscribe(
      {
        next: (data) => {
          this.post = data;
        }, error: (error) => {
          console.error(error)
        }
      }
    )
  }

  private getCommentsForPost() {
    this.commentService.getAllCommentsForPost(this.postId).subscribe(
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