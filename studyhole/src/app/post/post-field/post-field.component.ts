import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { DataField, DataFields, TextField } from '../post-template-model';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-post-field',
  standalone: true,
  imports: [
    NgIf,
    NgFor,
    ReactiveFormsModule
  ],
  templateUrl: './post-field.component.html',
  styleUrl: './post-field.component.css'
})
export class PostFieldComponent {
  dataFieldOptions: string[] = Object.values(DataFields);

  @Input() childForm: FormGroup | undefined;

  static addField() : FormGroup{
    return new FormGroup(
      {
        fieldName: new FormControl ('', Validators.required),
        dataType: new FormControl ('', Validators.required),
      });
  }
}
