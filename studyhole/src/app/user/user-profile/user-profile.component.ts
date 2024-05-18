import { Component, OnInit } from '@angular/core';
import { PostModel } from '../../post/post-model';
import { CommentPayload } from '../../comment/comment.payload';
import { CommentService } from '../../comment/comment.service';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { PostService } from '../../post/post.service';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { PostTileComponent } from '../../post/post-tile/post-tile.component';
import { HeaderComponent } from '../../header/header.component';
import { FormsModule } from '@angular/forms';
import { SideBarComponent } from "../../shared/side-bar/side-bar.component";
import { CommunityService } from '../../community/community.service';
import { CommunityModel } from '../../community/community-model';

@Component({
    selector: 'app-user-profile',
    standalone: true,
    templateUrl: './user-profile.component.html',
    styleUrl: './user-profile.component.css',
    imports: [
        CommonModule,
        RouterOutlet,
        RouterLink,
        RouterLinkActive,
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
  subscribedCommunities?: CommunityModel[];
  communitiesLength?: number;
  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
    private commentService: CommentService, private comService: CommunityService) {
    this.name = this.activatedRoute.snapshot.params['name'];

    this.postService.getAllPostsByUser(this.name).subscribe(data => {
      this.posts = data;
      this.postLength = data.length;
    });
    this.commentService.getAllCommentsByUser(this.name).subscribe(data => {
      this.comments = data;
      this.commentLength = data.length;
    });
    this.comService.getAllCommunitiesbyUsername(this.name).subscribe(data => {
      this.subscribedCommunities = data;
      this.communitiesLength = data.length;
    });
  }

  ngOnInit(): void {
  }
}