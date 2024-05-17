import { CommonModule, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreatePostPayload } from './create-post.payload';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../post.service';
import { HeaderComponent } from '../../header/header.component';
import { ChooseTemplateService } from '../choose-template/choose-template.service';
import { PostInputComponent } from '../post-input/post-input.component';
import { DateField, ImageField, TextField, UrlField } from '../post-template-model';


@Component({
    selector: 'app-create-post',
    standalone: true,
    templateUrl: './create-post.component.html',
    styleUrl: './create-post.component.css',
    imports: [
        ReactiveFormsModule,
        CommonModule,
        HeaderComponent,
        PostInputComponent,
        NgFor
    ]
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup = new FormGroup({})
  postPayload?: CreatePostPayload;
  communityId?: number;

  constructor(private router: Router, private postService: PostService,
    private activatedRoute: ActivatedRoute, 
    private chooseService: ChooseTemplateService) {
    this.communityId = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit() {
    this.postPayload = this.chooseService.getCurrentPayload();
    console.log(this.postPayload);
    
    this.createPostForm = new FormGroup({
      postTitle: new FormControl('', Validators.required),
      fields: new FormArray([])
    });
    
    this.postPayload.postTemplate?.dataFields?.forEach(datafield => {
      this.getFieldsAsFormArray().push(this.addField());
    })
  }
  getFieldsAsFormArray(){
    return this.createPostForm.get('fields') as FormArray;
  }
  addField() : FormGroup{
    return PostInputComponent.addField();
  }
  createPost() {
    this.postPayload!.postTitle = this.postPayload?.postTitle;
    this.postPayload!.communityId = this.communityId;
    this.postPayload!.postTemplateId = this.postPayload?.postTemplate?.id;
    
    const fields = this.postPayload!.postTemplate!.dataFields!;
    const forms = this.getFieldsAsFormArray().controls;

    for(let i = 0; i < fields.length; i++){
      let input = forms[i].get('input')?.value;
      let field = fields[i]
      console.log(field.type);
      if (field.type == 'TextField'){
        const textField = field as TextField;
        textField.input! = input;
      } else if (field.type  == 'DateField'){
        console.error("NOT SET YET");
      } else if (field.type  == 'UrlField'){
        console.error("NOT SET YET");
      }else if (field.type  == 'ImageField'){
        console.error("NOT SET YET");
      }

    }

    this.postService.createPost(this.postPayload!, this.communityId!).subscribe(
      {
        next: (data) => {
        this.router.navigateByUrl('/view-post/' + data.postId);
      }, error: (error) => {
        console.error(error);
      }
    })
  }

  discardPost() {
    this.router.navigateByUrl('/view-community/' + this.communityId);
  }

}