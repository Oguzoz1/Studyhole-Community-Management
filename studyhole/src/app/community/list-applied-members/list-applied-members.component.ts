import { Component, Input } from '@angular/core';
import { UserModel } from '../../user/user-model';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommunityService } from '../community.service';
import { NgFor } from '@angular/common';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-list-applied-members',
  standalone: true,
  imports: [RouterLink, NgFor],
  templateUrl: './list-applied-members.component.html',
  styleUrl: './list-applied-members.component.css'
})
export class ListAppliedMembersComponent {
  @Input() members?: UserModel[];
  communityId?: number;
  constructor(private comService: CommunityService, private activatedRoute: ActivatedRoute,
     private toastr: ToastrService) {
    this.communityId = this.activatedRoute.snapshot.params['id'];
  }
  ngOnInit(): void {

  }

  acceptMember(user: UserModel){
    this.comService.acceptMember(this.communityId!, user).subscribe(
      {
        next: (data) => {
          this.toastr.success(user.username + 'accepted!');
        }
      }
    )
  }
}
