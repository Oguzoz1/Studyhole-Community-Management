import { CommonModule, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { SignupRequestPayload } from './signup-request.payload';
import { AuthService } from '../shared/auth.service';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { HeaderComponent } from '../../header/header.component';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ CommonModule, RouterOutlet, RouterLink, RouterLinkActive,ReactiveFormsModule, NgIf,HeaderComponent],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit {

  signupRequestPayload: SignupRequestPayload;

  signupForm: FormGroup = new FormGroup({}); 

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) { 
    this.signupRequestPayload = {
      username: '',
      email: '',
      password: ''
    };
  }

  ngOnInit() {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });

  }

  signup(){
    this.signupRequestPayload.username = this.signupForm.get('username')?.value;
    this.signupRequestPayload.email = this.signupForm.get('email')?.value;
    this.signupRequestPayload.password = this.signupForm.get('password')?.value;
    
    this.authService.signup(this.signupRequestPayload).subscribe({
      next: (data) =>{
        this.toastr.success(data);
        this.router.navigate(['/login'], {queryParams: {registered: 'true'}})
      },
      error: (error) => {
        this.toastr.error('Registration Failed! Try Again!');
      }
    }
  )
    
  }
}
