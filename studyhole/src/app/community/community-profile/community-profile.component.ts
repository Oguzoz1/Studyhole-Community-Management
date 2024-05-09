import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, ActivatedRoute } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { CommunityService } from '../community.service';
import { CommunityModel } from '../community-model';
import { UserService } from '../../user/user.service';
import { ToastrService } from 'ngx-toastr';
import { UserModel } from '../../user/user-model';
import { Observable, forkJoin } from 'rxjs';
import { ListCommunityMembersComponent } from "../list-community-members/list-community-members.component";

@Component({
    selector: 'app-community-profile',
    standalone: true,
    templateUrl: './community-profile.component.html',
    styleUrl: './community-profile.component.css',
    imports: [
        NgFor,
        NgIf,
        CommonModule,
        RouterOutlet,
        RouterLink,
        RouterLinkActive,
        HeaderComponent,
        ListCommunityMembersComponent
    ]
})
export class CommunityProfileComponent implements OnInit{

  communityId: number;
  community?: CommunityModel;
  currentUser?: UserModel;
  isSubscribed?: boolean;
  userCount?: number;
  isPublic?: boolean;
  constructor(private activatedRoute: ActivatedRoute, private comService: CommunityService,
    private usService: UserService, private toastr: ToastrService) {
      this.communityId = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    forkJoin({
      community: this.getCommunityById(),
      currentUser: this.setCurrentUser()

    }).subscribe(
      ({community, currentUser}) =>{
        this.community = community;
        this.currentUser = currentUser;
        this.isSubscribed = this.CurrentUserSubscribed();
        this.userCount = this.getUserCount();
        this.isPublic = community.publicCommunity;
        console.log(this.currentUser?.username);
        console.log(this.isSubscribed);
      }
    )
  }

  private getCommunityById() {
    return this.comService.getCommunityById(this.communityId);
  }

  public subscribeToCommunity(){
    this.usService.subscribeCommunity(this.communityId).subscribe(
      {
        next: () => {
          this.toastr.success('Subscribed!')
          this.isSubscribed = true;
        },
        error: (error) => {
          console.error(error);
          this.toastr.error('Something went wrong!')
        }
      }
    )
  }

  public leaveCommunity(){
    this.usService.leaveCommunity(this.communityId).subscribe(
      {
        next: () => {
          this.toastr.success('Left the community!')
          this.isSubscribed = false;
        },
        error: (error) => {
          console.error(error);
          this.toastr.error('Something went wrong!')
        }
      }
    )
  }
  getUserCount(): number{
    return this.community?.memberIds?.length ?? 0;
  }
  setCurrentUser(): Observable<UserModel>{
    return this.usService.getCurrentUserPackage();
  }

  public CurrentUserSubscribed(): boolean{
    if (this.community?.memberIds?.includes(this.currentUser?.userId!, 0)){
      return true;
    }
    else return false;
  }
  
  applyToCommunity() {
    throw new Error('Method not implemented.');
    }
    
}
