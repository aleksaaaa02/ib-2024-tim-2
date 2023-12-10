import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from "./feature-modules/account/account/account.component";
import { ResultsPageComponent } from "./feature-modules/accommodation/results-page/results-page.component";
import { AccommodationPageComponent } from "./feature-modules/accommodation/accommodation-page/accommodation-page.component";
import { LandingPageComponent } from "./feature-modules/accommodation/landing-page/landing-page.component";
import { LoginComponent } from './feature-modules/authentication/login/login.component';
import { RegistrationComponent } from './feature-modules/authentication/registration/registration.component';
import { ForgotPasswordComponent } from './feature-modules/authentication/forgot-password/forgot-password.component';
import { ConfirmationComponent } from './feature-modules/authentication/confirmation/confirmation.component';
import { AccommodationCreateComponent } from './feature-modules/accommodation/update/accommodation-create/accommodation-create.component';
import { CalendarComponent } from './feature-modules/accommodation/update/calendar/calendar.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "confirmation", component: ConfirmationComponent },
  { path: "results",component: ResultsPageComponent },
  { path: "accommodation/details/:accommodationId", component: AccommodationPageComponent },
  { path: '', component: LandingPageComponent },
  { path: 'accommodation/calendar/:accommodationId', component: CalendarComponent },
  { path: "account", component: AccountComponent },
  { path: "accommodation/create", component: AccommodationCreateComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
