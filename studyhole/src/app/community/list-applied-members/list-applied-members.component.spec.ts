import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAppliedMembersComponent } from './list-applied-members.component';

describe('ListAppliedMembersComponent', () => {
  let component: ListAppliedMembersComponent;
  let fixture: ComponentFixture<ListAppliedMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListAppliedMembersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListAppliedMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
