import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {
  hide1 = true;
  hide2 = true;
  countries: Promise<string[]> = Promise.resolve([]);
  
  form: FormGroup;
  submitted: boolean = false;

  constructor(private authenticationService: AuthenticationService, private fb: FormBuilder) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}')]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$')]],
      confirmPassword: ['', [Validators.required, this.passwordValidator]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      country: ['', Validators.required],
      city: ['', Validators.required],
      streetAddress: ['', Validators.required],
      zipCode: ['', Validators.required],
      phone: ['', Validators.required],
      role: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.countries = this.authenticationService.getCountries();
  }
  
  get passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!this.form) {
        return null;
      }
      const password = this.form.get('password')?.value;
      const confirmPassword = control.value;
      if (password !== confirmPassword) {
        return {different: true, message: 'Confirm password should be same as password'}
      }
      return null;
    }
  }

  onSubmit() {
    if(this.form.valid){
      console.log("validno");
    }
    this.submitted = true;
  }
}
