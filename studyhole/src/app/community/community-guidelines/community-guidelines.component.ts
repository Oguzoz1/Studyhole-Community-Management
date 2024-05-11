import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommunityService } from '../community.service';
import { CommunityModel } from '../community-model';
import { Observable, forkJoin } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { FormsModule, NgModel } from '@angular/forms';
import { UserModel } from '../../user/user-model';
import { UserService } from '../../user/user.service';

@Component({
  selector: 'app-community-guidelines',
  standalone: true,
  imports: [
    NgIf,
    FormsModule
  ],
  templateUrl: './community-guidelines.component.html',
  styleUrl: './community-guidelines.component.css'
})
export class CommunityGuidelinesComponent implements OnInit{
  guidelinesContent: string = "Put your guidelines here...";
  isEditMode: boolean = false;

  communityId: number;
  community?: CommunityModel;
  currentUser?: UserModel;

  constructor(private activatedRoute: ActivatedRoute, private comService: CommunityService,
  private toastr: ToastrService, private usService: UserService) 
  {
    this.communityId = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
      forkJoin({
        community: this.comService.getCommunityById(this.communityId),
        currentUser: this.getCurrentUser()

      }).subscribe(
        ({community,currentUser}) => {
          this.community = community;
          this.currentUser = currentUser;
          if (this.community.guidelines != this.guidelinesContent){
            this.guidelinesContent = this.community.guidelines!;
          }
        }
      )
  }
  
  isOwnerUser(): boolean{
    if (this.community?.ownerUsers?.some(ownerUser => ownerUser.userId == this.currentUser?.userId!))
      return true
    else return false
  }
  toggleEditMode() {
    this.isEditMode = !this.isEditMode;
    if (!this.isEditMode) {
      this.onSave();
    }
  }
  onContentChange(event: any) {
    this.guidelinesContent = event.target.value;
  }

  onSave() {
    this.community!.guidelines = this.guidelinesContent;
    console.info(this.community?.guidelines);
    this.comService.updateCommunity(this.community!).subscribe({
      next: () => {
        this.toastr.success("Guidelines Saved!")
      },
      error: (error) => {
        console.error(error);
        this.toastr.error("Something went wrong!")
      }
    })
    console.log('Saving guidelines:', this.guidelinesContent);
    
    this.isEditMode = false;
  }
  getCurrentUser(): Observable<UserModel>{
    return this.usService.getCurrentUserPackage();
  }

}
