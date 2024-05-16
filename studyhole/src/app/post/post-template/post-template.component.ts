import { NgComponentOutlet, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../post.service';
import { CommunityService } from '../../community/community.service';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataField, DataFields, DateField, ImageField, PostTemplateModel, TextField, UrlField } from '../post-template-model';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { PostFieldComponent } from "../post-field/post-field.component";
import { HeaderComponent } from "../../header/header.component";
import { forkJoin } from 'rxjs';
import { CommunityModel } from '../../community/community-model';
@Component({
    selector: 'app-post-template',
    standalone: true,
    templateUrl: './post-template.component.html',
    styleUrl: './post-template.component.css',
    imports: [
        NgFor,
        NgIf,
        FontAwesomeModule,
        ReactiveFormsModule,
        PostFieldComponent,
        NgComponentOutlet,
        HeaderComponent
    ]
})
export class PostTemplateComponent implements OnInit{

  showPlusSign = true;
  showForm = true;
  postTemplate?: PostTemplateModel;
  dataFieldOptions: string[] = Object.values(DataFields);
  faPlus = faPlus;
  templateFormGroup!: FormGroup;
  dataFields: DataField[] = [];
  community?: CommunityModel;
  communityId: number;
  fieldForm!: FormGroup;

  constructor(private router: Router, private postService: PostService,
    private comService: CommunityService, private activatedRoute: ActivatedRoute) {
      this.communityId = this.activatedRoute.snapshot.params['id'];
      this.postTemplate = new PostTemplateModel();
      }

  ngOnInit(): void {
    forkJoin({
      community: this.comService.getCommunityById(this.communityId)
    }).subscribe(
      ({community}) =>{
        this.community = community;
      }
    )

    this.templateFormGroup = new FormGroup({
      templateName: new FormControl('', Validators.required),
      fields: new FormArray([
        PostFieldComponent.addField(),
      ])
    });
  }

  addField() {
    this.getControls().push(PostFieldComponent.addField());
  }
  getControls(){
    return this.templateFormGroup.get('fields') as FormArray;
  }

  createTemplate(){
    console.log(this.templateFormGroup.value);

    for (let childForm of this.getControls().controls){
      let fieldName = childForm.get('fieldName')?.value;
      let dataType = childForm.get('dataType')?.value;
      console.log(fieldName + dataType);
      let newField: DataField | null = null;
            
      switch (dataType) {
        case DataFields.Text:
        newField = this.createTextField(fieldName);
        break;
        case DataFields.Image:
        newField = this.createImageField(fieldName);
        break;
        case DataFields.Date:
        newField = this.createDateField(fieldName);
        break;
        case DataFields.URL:
        newField = this.createUrlField(fieldName);
        break;
     }
      if (newField) 
      {
      this.dataFields?.push(newField);
      }
  }

  this.postTemplate!.dataFields = this.dataFields;
  this.postTemplate!.ownerCommunity = this.community;
  this.postTemplate!.templateName = this.templateFormGroup.get('templateName')?.value;
  this.postService.createPostTemplate(this.postTemplate!, this.communityId).subscribe(
    {
      next: (data) => {
      this.router.navigateByUrl('/view-community/' + this.communityId);
    }, error: (error) => {
      console.error(error);
    }
  })
  }
  
  //IOC VIOLATION
  private createTextField(fieldName: string): TextField {
      const textField = new TextField();
      textField.name = fieldName;
      return textField;
  }

  private createImageField(fieldName: string): ImageField {
      const imageField = new ImageField();
      imageField.name = fieldName;
      return imageField;
  }

  private createDateField(fieldName: string): DateField {
      const dateField = new DateField();
      dateField.name = fieldName;
      return dateField;
  }

  private createUrlField(fieldName: string): UrlField {
      const urlField = new UrlField();
      urlField.name = fieldName;
      return urlField;
  }
}
