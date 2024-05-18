import { Component, OnInit, contentChild } from '@angular/core';
import { PostModel } from '../post-model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentPayload } from '../../comment/comment.payload';
import { CommentService } from '../../comment/comment.service';
import { CommunitySideBarComponent } from '../../community/community-side-bar/community-side-bar.component';
import { SideBarComponent } from '../../shared/side-bar/side-bar.component';
import { VoteButtonComponent } from '../../shared/vote-button/vote-button.component';
import { CommonModule, DatePipe, NgFor, NgIf } from '@angular/common';
import { HeaderComponent } from '../../header/header.component';
import { CommunityService } from '../../community/community.service';
import { CommunityModel } from '../../community/community-model';
import { DataField, DateSField, ImageField, PostTemplateModel, TextField, UrlField } from '../post-template-model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ImageFieldComponent } from "../../image/image-field/image-field.component";

@Component({
    selector: 'app-view-post',
    standalone: true,
    templateUrl: './view-post.component.html',
    styleUrl: './view-post.component.css',
    imports: [
        CommunitySideBarComponent,
        ReactiveFormsModule,
        VoteButtonComponent,
        NgFor,
        HeaderComponent,
        SideBarComponent,
        NgIf,
        DatePipe,
        CommonModule,
        ImageFieldComponent
    ]
})
export class ViewPostComponent implements OnInit {
  
  postId: number;
  post?: PostModel;
  postTemplate?: PostTemplateModel;
  commentForm: FormGroup;
  commentPayload?: CommentPayload;
  comments?: CommentPayload[];
  
  constructor(private postService: PostService, private activateRoute: ActivatedRoute,
    private commentService: CommentService, private router: Router, private comServ: CommunityService) {
      this.postId = this.activateRoute.snapshot.params['id'];
      
      this.commentForm = new FormGroup({
        text: new FormControl('', Validators.required)
      });
      this.commentPayload = {
        text: '',
        postId: this.postId
      };
    }
    
    ngOnInit(): void {
      this.getPostById();
    }

    goToCommunity(name: string): void {
      this.comServ.getCommunityByName(name).subscribe((com: CommunityModel) => {
        console.log(com.communityId);
        com.ownerUsers?.forEach(user => {
          console.log(user.username);
          this.router.navigateByUrl('view-community/' + com.communityId);
        });
    
      });
    }
  postComment() {
    this.commentPayload!.text = this.commentForm?.get('text')?.value;

    this.commentService.postComment(this.commentPayload!).subscribe(
      {
        next: () => {
          this.commentForm?.get('text')?.setValue('');
          this.getCommentsForPost(this.postId);
        }, error: () =>{
          console.error("Error during commenting");
        }
      }
    )
  }
  getField(field: DataField): string {
    switch (field.type) {
      case 'TextField':
        const textField = field as TextField;
        console.log(textField.input);
        return textField.input!;
      case 'DateSField':
        const dateField = field as DateSField;
        return dateField.input?.toString()!;
      case 'UrlField':
        const urlField = field as UrlField;
        return this.ensureUrlProtocol(urlField.input?.toString()!);
      case 'ImageField':
        return 'ImageField content'; 
      default:
        return '';
    }
  }
  getImageId(field: DataField): number | undefined {
    if (field.type === 'ImageField') {
      return (field as ImageField).input;
    }
    return undefined;
  }
  
getImage(field: DataField): Observable<string> {
  const image = field as ImageField;
  
  // Return the Observable returned by getDatafieldImagebyImageId
  return this.postService.getDatafieldImagebyImageId(image.input!).pipe(
    map(data => 'data:image/jpeg;base64,' + data?.imageData!)
  );
}

  private getPostById() {
    this.postService.getPost(this.postId).subscribe(
      {
        next: (data) => {
          this.post = data;
          this.getCommentsForPost(this.post.postId!);
        }, error: (error) => {
          console.error(error)
        }
      }
    )
  }

  private ensureUrlProtocol(url: string): string {
    if (!url) {
      return '';
    }
    // Check if the URL starts with http:// or https://
    if (!/^https?:\/\//i.test(url)) {
      return 'http://' + url;
    }
    return url;
  }
  private getCommentsForPost(id:number) {
    this.commentService.getAllCommentsForPost(id).subscribe(
      {
        next: (data) => {
          this.comments = data;
        }, error: (error) => {
          console.error(error)
        }
      }
    )
  }

}