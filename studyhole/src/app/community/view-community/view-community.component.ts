import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommunityProfileComponent } from '../community-profile/community-profile.component';
import { CommunityService } from '../community.service';
import { CommunityModel } from '../community-model';
import { PostModel } from '../../shared/post-model';
import { PostService } from '../../shared/post.service';
import { VoteButtonComponent } from "../../shared/vote-button/vote-button.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { SideBarComponent } from "../../shared/side-bar/side-bar.component";
import { ListCommunityMembersComponent } from "../list-community-members/list-community-members.component";
import { CommunityGuidelinesComponent } from "../community-guidelines/community-guidelines.component";

@Component({
    selector: 'app-view-community',
    standalone: true,
    templateUrl: './view-community.component.html',
    styleUrl: './view-community.component.css',
    imports: [
        CommonModule,
        RouterOutlet,
        RouterLink,
        RouterLinkActive,
        HeaderComponent,
        CommunityProfileComponent,
        NgFor,
        NgIf,
        VoteButtonComponent,
        FontAwesomeModule,
        SideBarComponent,
        ListCommunityMembersComponent,
        CommunityGuidelinesComponent
    ]
})
export class ViewCommunityComponent implements OnInit {
  
faComments = faComments;

  communityId: number;
  posts?: Array<PostModel>;
  
  constructor(private activateRoute: ActivatedRoute, private postService: PostService, private router: Router) {
      this.communityId = this.activateRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.postService.getAllPostsByCommunity(this.communityId).subscribe(
      {
        next: (data) => {
          this.posts = data;
        },error: (error) => {
          console.error(error);
        }
      }
    )
  }

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
    
}
