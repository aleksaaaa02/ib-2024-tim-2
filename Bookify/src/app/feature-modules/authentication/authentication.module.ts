import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field'
import { LoginComponent } from './login/login.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { RegistrationComponent } from './registration/registration.component';
import { MatSelectModule } from '@angular/material/select';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component'
import { RouterModule, Routes } from '@angular/router';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatButtonModule } from '@angular/material/button';
import {ReactiveFormsModule} from "@angular/forms";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "confirmation", component: ConfirmationComponent }
];

@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    ConfirmationComponent,
    ForgotPasswordComponent
  ],
    imports: [
        CommonModule,
        MatFormFieldModule,
        BrowserModule,
        BrowserAnimationsModule,
        MatInputModule,
        MatIconModule,
        MatSelectModule,
        RouterModule.forChild(routes),
        MatButtonModule,
        MatButtonToggleModule,
        ReactiveFormsModule
    ],
  exports: [
    LoginComponent,
    RegistrationComponent,
    ConfirmationComponent,
    ForgotPasswordComponent,
    RouterModule
  ]
})
export class AuthenticationModule { }
