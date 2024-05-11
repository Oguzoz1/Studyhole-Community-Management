import { Component } from '@angular/core';

@Component({
  selector: 'app-community-guidelines',
  standalone: true,
  imports: [],
  templateUrl: './community-guidelines.component.html',
  styleUrl: './community-guidelines.component.css'
})
export class CommunityGuidelinesComponent {
  guidelinesContent: string = "Put your guidelines here...";
  isEditMode: boolean = false;

  toggleEditMode() {
    this.isEditMode = !this.isEditMode;
  }
}
