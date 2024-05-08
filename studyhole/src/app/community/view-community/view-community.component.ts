import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommunityProfileComponent } from '../community-profile/community-profile.component';
import { CommunityService } from '../community.service';

@Component({
  selector: 'app-view-community',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    HeaderComponent,
    CommunityProfileComponent
  ],
  templateUrl: './view-community.component.html',
  styleUrl: './view-community.component.css'
})
export class ViewCommunityComponent implements OnInit {

  communityId: number;


  constructor(private activateRoute: ActivatedRoute, private comService: CommunityService) {
      this.communityId = this.activateRoute.snapshot.params['id'];
  }

  ngOnInit(): void {

  }

}
