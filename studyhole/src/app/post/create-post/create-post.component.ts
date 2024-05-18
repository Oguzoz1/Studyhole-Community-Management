import { CommonModule, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreatePostPayload } from './create-post.payload';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../post.service';
import { HeaderComponent } from '../../header/header.component';
import { ChooseTemplateService } from '../choose-template/choose-template.service';
import { PostInputComponent } from '../post-input/post-input.component';
import { DateSField, ImageField, TextField, UrlField } from '../post-template-model';
import { ImageUploadService } from '../../shared/image-upload-service';


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
    private chooseService: ChooseTemplateService,
    private imageService: ImageUploadService) {
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
    const postPayload = this.postPayload;
    const forms = this.getFieldsAsFormArray().controls;
  
    if (!postPayload || !postPayload.postTemplate) {
      return;
    }
  
    postPayload.postTitle = postPayload.postTitle;
    postPayload.communityId = this.communityId;
    postPayload.postTemplateId = postPayload.postTemplate.id;
    postPayload.content = postPayload.content || [];
  
    const fields = postPayload.postTemplate.dataFields || [];
  
    fields.forEach((field, index) => {
      const input = forms[index].get('input')?.value;
      let newField;
  
      switch (field.type) {
        case 'TextField':
          newField = new TextField();
          break;
        case 'DateSField':
          newField = new DateSField();
          break;
        case 'UrlField':
          newField = new UrlField();
          break;
        case 'ImageField':
          newField = new ImageField();
          newField.name = field.name;
          newField.file = input;
          this.postPayload!.content!.push(newField);
          console.log(newField);
          //set file
          return;
        default:
          console.error(`Unknown field type: ${field.type}`);
          return;
      }
  
      newField.name = field.name;
      newField.input = input;
      this.postPayload!.content!.push(newField);
      console.log(newField);
    });
  

    this.postService.createPost(this.postPayload!, this.communityId!).subscribe(
      {
        next: (data) => {
        console.log(data);

        //iterate post image fields in payload and upload images 
        for (let index = 0; index < data!.content!.length; index++) {
          let element = data!.content![index];
          let elementPayload = this.postPayload!.content![index];
          if (element.type == 'ImageField'){
            const el = element as ImageField;
            const elPay = elementPayload as ImageField;
            console.log(el.file!);
            //upload
            this.imageService.postDatafieldUploadbyImageId(elPay.file!, el.id!).subscribe(
              {
                next:(date) => {
                  console.log(date);
                  this.router.navigateByUrl('/view-post/' + data.postId!);

                }, error: (error) =>{
                  console.error(error);
                }
              }
            );
          }
        }
      }, error: (error) => {
        console.error(error);
      }
    })
  }

  discardPost() {
    this.router.navigateByUrl('/view-community/' + this.communityId);
  }

}