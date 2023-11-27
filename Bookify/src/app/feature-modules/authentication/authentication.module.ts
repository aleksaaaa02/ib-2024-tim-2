import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field'
import { LoginComponent } from './login/login.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { RegistrationComponent } from './registration/registration.component';
import { MatSelectModule } from '@angular/material/select'

@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatIconModule, 
    MatSelectModule
  ],
  exports: [
    LoginComponent,
    RegistrationComponent
  ]
})
export class AuthenticationModule { }
