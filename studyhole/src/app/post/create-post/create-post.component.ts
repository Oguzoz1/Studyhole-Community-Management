import { CommonModule, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreatePostPayload } from './create-post.payload';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../post.service';
import { HeaderComponent } from '../../header/header.component';
import { ChooseTemplateService } from '../choose-template/choose-template.service';
import { PostInputComponent } from '../post-input/post-input.component';
import { DateSField, TextField } from '../post-template-model';


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
    
    this.createPostForm = new FormGroup({
      postTitle: new FormControl('', Validators.required),
      fields: new FormArray([])
    });
    
    this.postPayload.postTemplate?.dataFields?.forEach(datafield => {
      console.log("A");
      this.getFieldsAsFormArray().push(PostInputComponent.addField(datafield!.type!));
    })
  }
  getFieldsAsFormArray(){
    return this.createPostForm.get('fields') as FormArray;
  }

  createPost() {
    this.postPayload!.postTitle = this.postPayload?.postTitle;
    this.postPayload!.communityId = this.communityId;
    this.postPayload!.postTemplateId = this.postPayload?.postTemplate?.id;

    const fields = this.postPayload!.postTemplate!.dataFields!;
    const forms = this.getFieldsAsFormArray().controls;

    if (this.postPayload?.content == null) {
      this.postPayload!.content = []; 
    }

    for(let i = 0; i < fields.length; i++){
      let input = forms[i].get('input')?.value;
      let field = fields[i]
      console.log(field.type);
      if (field.type == 'TextField'){
        const textField = new TextField();
        textField.name = field.name;
        textField.input! = input;
        this.postPayload?.content?.push(textField);
      } else if (field.type  == 'DateSField'){
        const dateField = new DateSField();
        dateField.name = field.name;
        dateField.input! = input;
        this.postPayload?.content?.push(dateField);
        console.log(this.postPayload?.content?.[i]);
      } else if (field.type  == 'UrlField'){
        console.error("NOT SET YET");
      }else if (field.type  == 'ImageField'){
        //If its image.
        console.error("NOT SET YET");
      }

    }

    this.postService.createPost(this.postPayload!, this.communityId!).subscribe(
      {
        next: (data) => {
        console.log(data);
        this.router.navigateByUrl('/view-post/' + data.postId!);
      }, error: (error) => {
        console.error(error);
      }
    })
  }

  discardPost() {
    this.router.navigateByUrl('/view-community/' + this.communityId);
  }

}