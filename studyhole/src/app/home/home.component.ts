import { Component, OnInit } from '@angular/core';
import { PostModel } from '../shared/post-model';
import { PostService } from '../shared/post.service';
import { HeaderComponent } from '../header/header.component';
import { SignupComponent } from '../auth/signup/signup.component';
import { LoginComponent } from '../auth/login/login.component';
import { SideBarComponent } from '../shared/side-bar/side-bar.component';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { PostTileComponent } from '../post/post-tile/post-tile.component';
import { AuthService } from '../auth/shared/auth.service';
import { CommunitySideBarComponent } from '../community/community-side-bar/community-side-bar.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    HeaderComponent,
    SignupComponent, 
    LoginComponent,
    SideBarComponent,
    PostTileComponent,
    CommunitySideBarComponent,
    NgFor,
    NgIf
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  posts: Array<PostModel> = [];
  isLoggedIn?: boolean;

  constructor(private postService: PostService, private authService: AuthService) {
    this.postService.getAllPosts().subscribe(post => {
      this.posts = post;
    });

    this.isLoggedIn = authService.isLoggedIn();
    console.log(authService.getUserName());
  }

  ngOnInit(): void {
  }
}