import { CUSTOM_ELEMENTS_SCHEMA, Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { PostModel } from '../post-model';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { VoteButtonComponent } from '../vote-button/vote-button.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HeaderComponent } from '../../header/header.component';

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

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
}