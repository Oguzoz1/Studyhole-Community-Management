import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, ActivatedRoute } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { CommunityService } from '../community.service';
import { CommunityModel } from '../community-model';
import { UserService } from '../../user/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-community-profile',
  standalone: true,
  imports: [
    NgFor,
    NgIf,
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    HeaderComponent,
  ],
  templateUrl: './community-profile.component.html',
  styleUrl: './community-profile.component.css'
})
export class CommunityProfileComponent implements OnInit{

  communityId: number;
  community?: CommunityModel;

  constructor(private activatedRoute: ActivatedRoute, private comService: CommunityService,
    private usService: UserService, private toastr: ToastrService
  ) {
      this.communityId = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.getCommunityById();
  }

  private getCommunityById() {
    this.comService.getCommunityById(this.communityId).subscribe(
      {
        next: (data) => {
          this.community = data;
        }, error: (error) => {
          console.error(error)
        }
      }
    )
  }

  public subscribeToCommunity(){
    this.usService.subscribeCommunity(this.communityId).subscribe(
      {
        next: () => {
          this.toastr.success('Subscribed!')
        },
        error: (error) => {
          console.error(error);
          this.toastr.error('Something went wrong!')
        }
      }
    )
  }
}
