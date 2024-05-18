import { DatePipe, NgFor, NgIf } from '@angular/common';
import { Component, Input, input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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
        ImageComponent,
        DatePipe
    ]
})
export class PostInputComponent {

  @Input() childForm: FormGroup | undefined;
  @Input() fieldName: string | undefined;
  @Input() type: string | undefined;

  static addField(type: string) : FormGroup{
    switch (type) {
      case 'TextField':
        return new FormGroup({
          input: new FormControl('', Validators.required)
        });
      case 'DateSField':
        return new FormGroup({
          input: new FormControl('', [Validators.required, PostInputComponent.dateValidator])
        });
      case 'UrlField':
        return new FormGroup({
          input: new FormControl('', Validators.required)
        });
        case 'ImageField':
        return new FormGroup({
          input: new FormControl(null, Validators.required) 
        });
      default:
        return new FormGroup({
          input: new FormControl('', Validators.required)
        });
    }
  }
  
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.childForm?.get('input')?.setValue(file);
  }

  static dateValidator(control: FormControl): { [key: string]: boolean } | null {
    const value = control.value;
    const regex = /^\d{2}\/\d{2}\/\d{4}$/; 
    if (value && !regex.test(value)) {
        return { 'invalidDate': true };
    }
    return null;
}
}
