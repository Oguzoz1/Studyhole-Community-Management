import { Routes } from '@angular/router';
import { SignupComponent } from './auth/signup/signup.component';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { canActivate } from './auth/auth.guard';
import { CreateCommunityComponent } from './community/create-community/create-community.component';
import { ViewPostComponent } from './post/view-post/view-post.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { ListCommunitiesComponent } from './community/list-communities/list-communities.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'sign-up', component: SignupComponent},
    {path: 'login', component: LoginComponent},
    {path: 'view-post/:id', component: ViewPostComponent},
    {path: 'user-profile/:name', component: UserProfileComponent},
    {path: 'list-communities', component: ListCommunitiesComponent},
    {path: 'create-post', component: CreatePostComponent},
    {path: 'create-community', component: CreateCommunityComponent}
];
