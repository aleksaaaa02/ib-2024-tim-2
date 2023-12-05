import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AccountComponent} from "./account/account/account.component";
import { ResultsPageComponent } from "./feature-modules/accommodation/results-page/results-page.component";
import { AccommodationPageComponent } from "./feature-modules/accommodation/accommodation-page/accommodation-page.component";
import { LandingPageComponent } from "./feature-modules/accommodation/landing-page/landing-page.component";
import { LoginComponent } from './feature-modules/authentication/login/login.component';
import { RegistrationComponent } from './feature-modules/authentication/registration/registration.component';
import { ForgotPasswordComponent } from './feature-modules/authentication/forgot-password/forgot-password.component';
import { ConfirmationComponent } from './feature-modules/authentication/confirmation/confirmation.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "confirmation", component: ConfirmationComponent },
  { path: "results",component: ResultsPageComponent },
  { path: "accommodation", component: AccommodationPageComponent },
  { path: '', component: LandingPageComponent },
  { path:"account", component: AccountComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
