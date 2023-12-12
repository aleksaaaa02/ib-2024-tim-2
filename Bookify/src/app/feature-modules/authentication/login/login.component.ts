import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from "../authentication.service";
import {Credentials} from "../model/credentials";
import {UserJWT} from "../model/UserJWT";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  credentialsForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  hide = true;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  getErrorMessage() {
    if (this.credentialsForm.controls['email'].hasError('required')) {
      return 'You must enter a value';
    }

    return this.credentialsForm.controls['email'].hasError('email') ? 'Not a valid email' : '';
  }

  login(): void {
    if(this.credentialsForm.valid){
      const credentials: Credentials = {
        email: this.credentialsForm.value.email || "",
        password: this.credentialsForm.value.password || ""
      }
      this.authenticationService.login(credentials).subscribe({
        next: (response: UserJWT) => {
         localStorage.setItem('user', response.accessToken);
         this.authenticationService.setUser();
         this.router.navigate(['']);
        }
      });
    }
  }
}
