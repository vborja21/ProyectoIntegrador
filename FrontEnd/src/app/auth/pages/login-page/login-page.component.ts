import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/models/LoginRequest';
import { AuthService } from 'src/app/services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  loginForm: FormGroup;
  error: string = "";

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }


  onLogin() {
    if (this.loginForm.valid) {
      const usernameControl = this.loginForm.get('username');
      const passwordControl = this.loginForm.get('password');

      if (usernameControl && passwordControl) {
        const credentials: LoginRequest = {
          username: usernameControl.value,
          password: passwordControl.value
        };

        this.authService.login(credentials).subscribe(
          data => {
            console.log('Login successful:', data);
            this.router.navigate(['/dashboard']);
          },
          error => {
            console.log(credentials);
            console.error('Login error:', error);
            this.error = 'Username or password is incorrect';
          }
        );
      } else {
        this.error = 'Form error: Username or password field is missing';
      }
    }
  }
}
