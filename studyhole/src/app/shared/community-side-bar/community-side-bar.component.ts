import { Component, OnInit } from '@angular/core';
import { CommunityModel } from '../../community/community-model';
import { CommunityService } from '../../community/community.service';
import { CommonModule, NgIf } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-community-side-bar',
  standalone: true,
  imports: [
    NgIf,
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './community-side-bar.component.html',
  styleUrl: './community-side-bar.component.css'
})
export class CommunitySideBarComponent implements OnInit {
  communities: Array<CommunityModel> = [];
  displayViewAll?: boolean;

  constructor(private communityService: CommunityService) {
    this.communityService.getAllCommunities().subscribe(data => {
      if (data.length >= 3) {
        this.communities = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.communities = data;
      }
    });
  }

  ngOnInit(): void { }

}