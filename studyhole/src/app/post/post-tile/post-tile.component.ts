import { CUSTOM_ELEMENTS_SCHEMA, Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { PostModel } from '../post-model';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { VoteButtonComponent } from '../../shared/vote-button/vote-button.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommunityService } from '../../community/community.service';
import { CommunityModel } from '../../community/community-model';
import { PostTemplateModel } from '../post-template-model';

@Component({
  selector: 'app-post-tile',
  standalone: true,
  imports: [CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    VoteButtonComponent,
    FontAwesomeModule,
  ],
  templateUrl: './post-tile.component.html',
  styleUrl: './post-tile.component.css',
  encapsulation: ViewEncapsulation.None,
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PostTileComponent implements OnInit {

  faComments = faComments;
  @Input() posts?: PostModel[];

  constructor(private router: Router, private comServ: CommunityService) { }

  ngOnInit(): void {
    this.posts?.reverse();
  }

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }

  goToCommunity(name: string): void {
    this.comServ.getCommunityByName(name).subscribe((com: CommunityModel) => {
      console.log(com.communityId);
      // Handle Hibernate proxies
      com.ownerUsers?.forEach(user => {
        // Access user properties to force initialization
        console.log(user.username);
        // Add additional handling as needed
        this.router.navigateByUrl('view-community/' + com.communityId);
      });
  
    });
  }
  
}