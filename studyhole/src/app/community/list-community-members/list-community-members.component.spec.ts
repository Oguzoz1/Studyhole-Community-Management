import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCommunityMembersComponent } from './list-community-members.component';

describe('ListCommunityMembersComponent', () => {
  let component: ListCommunityMembersComponent;
  let fixture: ComponentFixture<ListCommunityMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListCommunityMembersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListCommunityMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
