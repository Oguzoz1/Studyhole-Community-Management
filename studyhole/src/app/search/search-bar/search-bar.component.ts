import { Component, OnInit } from '@angular/core';
import { CommunityModel } from '../../community/community-model';
import { CommunityService } from '../../community/community.service';
import { forkJoin } from 'rxjs';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { UserModel } from '../../user/user-model';
import { UserService } from '../../user/user.service';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [
    NgFor,
    FormsModule,
    RouterLink,
    NgIf
  ],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent implements OnInit{

  searchQuery: string = '';
  communityList?: CommunityModel[];
  filteredCommunities? : CommunityModel[];
  filteredUsers?: UserModel[];
  userList?: UserModel[];
  filterUser: boolean = true;
  filterCommunity: boolean = true;
  filterOption: string = 'communities'; 

  constructor(private comService: CommunityService, private userService: UserService) {
    
  }

  ngOnInit() {
    forkJoin({
      comList: this.comService.getAllCommunities(),
      userList: this.userService.getAllUsers()
    }).subscribe(
      ({comList, userList}) => {
        this.communityList = comList;
        this.userList = userList;
      }
    )
  }

  filterItems() {
    const query = this.searchQuery.trim().toLowerCase();
    
    if (this.filterOption === 'communities') {
      this.filteredCommunities = this.communityList!.filter(community => 
        community.name!.toLowerCase().includes(query)
      ).sort((a, b) => {
        const nameA = a.name!.toLowerCase();
        const nameB = b.name!.toLowerCase();
        const startsWithQueryA = nameA.startsWith(query);
        const startsWithQueryB = nameB.startsWith(query);
  
        if (startsWithQueryA && !startsWithQueryB) {
          return -1; 
        } else if (!startsWithQueryA && startsWithQueryB) {
          return 1; 
        } else {
          return nameA.localeCompare(nameB); 
        }
      }).slice(0, 5);
    } else if (this.filterOption === 'users') {
      if (!query) {
        this.filteredUsers = [];
      } else {
        this.filteredUsers = this.userList!.filter(user => 
          user.username!.toLowerCase().includes(query)
        ).sort((a, b) => {
          const nameA = a.username!.toLowerCase();
          const nameB = b.username!.toLowerCase();
          const startsWithQueryA = nameA.startsWith(query);
          const startsWithQueryB = nameB.startsWith(query);
  
          if (startsWithQueryA && !startsWithQueryB) {
            return -1; 
          } else if (!startsWithQueryA && startsWithQueryB) {
            return 1;
          } else {
            return nameA.localeCompare(nameB); 
          }
        }).slice(0, 5);
      }
    }
  }
  
}

