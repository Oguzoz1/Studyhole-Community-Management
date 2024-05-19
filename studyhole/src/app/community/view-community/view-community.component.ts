import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommunityProfileComponent } from '../community-profile/community-profile.component';
import { CommunityService } from '../community.service';
import { CommunityModel } from '../community-model';
import { PostModel } from '../../post/post-model';
import { PostService } from '../../post/post.service';
import { VoteButtonComponent } from "../../shared/vote-button/vote-button.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { SideBarComponent } from "../../shared/side-bar/side-bar.component";
import { ListCommunityMembersComponent } from "../list-community-members/list-community-members.component";
import { CommunityGuidelinesComponent } from "../community-guidelines/community-guidelines.component";
import { PostTemplateComponent } from '../../post/post-template/post-template.component';
import { UserModel } from '../../user/user-model';
import { Observable, forkJoin } from 'rxjs';
import { UserService } from '../../user/user.service';
import { ToastrService } from 'ngx-toastr';
import { ListAppliedMembersComponent } from "../list-applied-members/list-applied-members.component";

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
        CommunityGuidelinesComponent,
        PostTemplateComponent,
        ListAppliedMembersComponent
    ]
})
export class ViewCommunityComponent implements OnInit {
  
  faComments = faComments;
  community?: CommunityModel;
  communityId: number;
  posts?: Array<PostModel>;
  currentUser?: UserModel;
  appliedMembers?: UserModel[];

  constructor(private activateRoute: ActivatedRoute, private postService: PostService, private router: Router,
    private usService: UserService, private comService: CommunityService, private toastr: ToastrService) {
      this.communityId = this.activateRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    forkJoin({
      community: this.getCommunityById(),
      currentUser: this.setCurrentUser(),
      applieds: this.getAppliedMembers()
    }).subscribe(
      ({community, currentUser, applieds}) =>{
        this.community = community;
        this.currentUser = currentUser;
        this.appliedMembers = applieds;
      }
    )

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

  isOwnerUser(): boolean{
    if (this.community?.ownerUsers?.some(ownerUser => ownerUser.userId == this.currentUser?.userId!))
      return true
    else return false
  }
  isMemberUser(): boolean{
    if(this.community?.memberIds?.includes(this.currentUser?.userId!)){
      return true;
    }
    else return false;
  }
  setCurrentUser(): Observable<UserModel>{
    return this.usService.getCurrentUserPackage();
  }
  getCommunityById() {
    return this.comService.getCommunityById(this.communityId);
  }
  getAppliedMembers(): Observable<UserModel[]>{
    return this.comService.getAllAppliedMembers(this.communityId);
  }
  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
  
  navigateToPostTemplate(id: number): void{
    this.router.navigateByUrl('/create-template/' + id);
  }
}
