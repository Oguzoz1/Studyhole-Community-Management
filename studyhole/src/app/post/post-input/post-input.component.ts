import { NgFor, NgIf } from '@angular/common';
import { Component, Input, input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataFields } from '../post-template-model';
import { ImageComponent } from "../../shared/image/image.component";

@Component({
    selector: 'app-post-input',
    standalone: true,
    templateUrl: './post-input.component.html',
    styleUrl: './post-input.component.css',
    imports: [
        NgIf,
        NgFor,
        ReactiveFormsModule,
        ImageComponent
    ]
})
export class PostInputComponent {

  @Input() childForm: FormGroup | undefined;
  @Input() fieldName: string | undefined;

  static addField() : FormGroup{
    return new FormGroup(
      {
        input: new FormControl('', Validators.required)
      }
    )
  }
}
