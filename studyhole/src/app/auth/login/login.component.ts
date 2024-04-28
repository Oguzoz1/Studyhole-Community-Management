import { CommonModule, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginRequestPayload } from './login-request.payload';
import { AuthService } from '../shared/auth.service';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { HeaderComponent } from '../../header/header.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, HeaderComponent,ReactiveFormsModule, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({});
  loginRequestPayload: LoginRequestPayload;
  isError: boolean = false;

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute,
    private router: Router, private toastr: ToastrService
  ) {    
    this.loginRequestPayload = {
      username: '',
      password: '',
    }
  }

  ngOnInit(): void{
      this.loginForm = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
      })
  }

  login(){
    this.loginRequestPayload.username = this.loginForm.get('username')?.value;
    this.loginRequestPayload.password = this.loginForm.get('password')?.value;

    this.authService.login(this.loginRequestPayload).subscribe({
      next: (data) => {
        this.isError = false;
        this.router.navigateByUrl('');
        if(this.authService.isLoggedIn()){
          this.toastr.success('Login Successful');
        }
      },
      error: (error) => {
        this.isError = true;
        this.toastr.error('Login Failed!');
      }
    });
  }
}
