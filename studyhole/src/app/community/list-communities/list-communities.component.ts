import { Component, OnInit } from '@angular/core';
import { CommunityModel } from '../community-model';
import { CommunityService } from '../community.service';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { SideBarComponent } from '../../shared/side-bar/side-bar.component';

@Component({
  selector: 'app-list-communities',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    HeaderComponent,
    SideBarComponent
  ],
  templateUrl: './list-communities.component.html',
  styleUrl: './list-communities.component.css'
})
export class ListCommunitiesComponent implements OnInit {

  communities?: Array<CommunityModel>;
  constructor(private communityService: CommunityService) { }

  ngOnInit() {
    this.communityService.getAllCommunities().subscribe( 
      {
        next: (data) => {
          this.communities = data;
        },
        error: (error) => {
          console.error(error);
        }
      }
    )
  }
}