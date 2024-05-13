import { Component, OnInit } from '@angular/core';
import { PostModel } from '../../shared/post-model';
import { CommentPayload } from '../../comment/comment.payload';
import { CommentService } from '../../comment/comment.service';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../../shared/post.service';
import { NgFor, NgIf } from '@angular/common';
import { PostTileComponent } from '../../post/post-tile/post-tile.component';
import { HeaderComponent } from '../../header/header.component';
import { FormsModule } from '@angular/forms';
import { SideBarComponent } from "../../shared/side-bar/side-bar.component";

@Component({
    selector: 'app-user-profile',
    standalone: true,
    templateUrl: './user-profile.component.html',
    styleUrl: './user-profile.component.css',
    imports: [
        HeaderComponent,
        PostTileComponent,
        NgFor,
        FormsModule,
        NgIf,
        SideBarComponent
    ]
})
export class UserProfileComponent implements OnInit {
  name: string;
  posts?: PostModel[];
  comments?: CommentPayload[];
  postLength?: number;
  commentLength?: number;
  selectedOption: string = 'posts'; 

  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
    private commentService: CommentService) {
    this.name = this.activatedRoute.snapshot.params['name'];

    this.postService.getAllPostsByUser(this.name).subscribe(data => {
      this.posts = data;
      this.postLength = data.length;
    });
    this.commentService.getAllCommentsByUser(this.name).subscribe(data => {
      this.comments = data;
      this.commentLength = data.length;
    });
  }

  ngOnInit(): void {
  }
}