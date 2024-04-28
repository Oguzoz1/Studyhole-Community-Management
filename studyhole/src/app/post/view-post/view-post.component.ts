import { Component, OnInit } from '@angular/core';
import { PostModel } from '../../shared/post-model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../../shared/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentPayload } from '../../comment/comment.payload';
import { CommentService } from '../../comment/comment.service';
import { CommunitySideBarComponent } from '../../shared/community-side-bar/community-side-bar.component';
import { SideBarComponent } from '../../shared/side-bar/side-bar.component';
import { VoteButtonComponent } from '../../shared/vote-button/vote-button.component';
import { NgFor } from '@angular/common';
import { HeaderComponent } from '../../header/header.component';

@Component({
  selector: 'app-view-post',
  standalone: true,
  imports: [
    CommunitySideBarComponent,
    SideBarComponent,
    ReactiveFormsModule,
    VoteButtonComponent,
    NgFor,
    HeaderComponent
  ],
  templateUrl: './view-post.component.html',
  styleUrl: './view-post.component.css'
})
export class ViewPostComponent implements OnInit {

  postId: number;
  post?: PostModel;
  commentForm: FormGroup;
  commentPayload?: CommentPayload;
  comments?: CommentPayload[];

  constructor(private postService: PostService, private activateRoute: ActivatedRoute,
    private commentService: CommentService, private router: Router) {
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