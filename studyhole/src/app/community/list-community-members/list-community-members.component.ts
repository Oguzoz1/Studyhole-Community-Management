import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { UserModel } from '../../user/user-model';
import { CommunityService } from '../community.service';
import { Observable, forkJoin } from 'rxjs';

@Component({
  selector: 'app-list-community-members',
  standalone: true,
  imports: [
    NgFor,
    NgIf,
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './list-community-members.component.html',
  styleUrl: './list-community-members.component.css'
})
export class ListCommunityMembersComponent implements OnInit{
  communityId: number;
  members?: UserModel[];
  constructor(private comService: CommunityService, private activatedRoute: ActivatedRoute) {
    this.communityId = this.activatedRoute.snapshot.params['id'];
  }
  ngOnInit(): void {
    forkJoin({
      members: this.getMembers()

    }).subscribe(
      ({members}) =>{
        this.members = members;
        console.log(this.members);
      }
    )
  }


  getMembers(): Observable<UserModel[]>{
    return this.comService.getMemberUsers(this.communityId);
  }
}
