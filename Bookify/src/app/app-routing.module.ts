import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from "./account/account/account.component";
import { ResultsPageComponent } from "./feature-modules/accommodation/results-page/results-page.component";
import { AccommodationPageComponent } from "./feature-modules/accommodation/accommodation-page/accommodation-page.component";
import { LandingPageComponent } from "./feature-modules/accommodation/landing-page/landing-page.component";
import { LoginComponent } from './feature-modules/authentication/login/login.component';
import { RegistrationComponent } from './feature-modules/authentication/registration/registration.component';
import { ForgotPasswordComponent } from './feature-modules/authentication/forgot-password/forgot-password.component';
import { ConfirmationComponent } from './feature-modules/authentication/confirmation/confirmation.component';
import { AccommodationCreateComponent } from './feature-modules/accommodation/update/accommodation-create/accommodation-create.component';
import { AccommodationLocationComponent } from './feature-modules/accommodation/update/accommodation-location/accommodation-location.component';
import { AccommodationAmenitiesComponent } from './feature-modules/accommodation/update/accommodation-amenities/accommodation-amenities.component';
import { AccommodationPhotosComponent } from './feature-modules/accommodation/update/accommodation-photos/accommodation-photos.component';
import { AccommodationGuestsComponent } from './feature-modules/accommodation/update/accommodation-guests/accommodation-guests.component';
import { AccommodationAvailabilityComponent } from './feature-modules/accommodation/update/accommodation-availability/accommodation-availability.component';
import { CalendarComponent } from './feature-modules/accommodation/update/calendar/calendar.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "confirmation", component: ConfirmationComponent },
  { path: "results",component: ResultsPageComponent },
  { path: "accommodation", component: AccommodationPageComponent },
  { path: '', component: LandingPageComponent },
  { path: 'calendar/:accommodationId', component: CalendarComponent },
  { path: "account", component: AccountComponent },
  { path: "accommodation/create/basic-info", component: AccommodationCreateComponent },
  { path: "accommodation/create/location", component: AccommodationLocationComponent },
  { path: "accommodation/create/amenities", component: AccommodationAmenitiesComponent },
  { path: "accommodation/create/photos", component: AccommodationPhotosComponent },
  { path: "accommodation/create/guests", component: AccommodationGuestsComponent },
  { path: "accommodation/create/availability", component: AccommodationAvailabilityComponent },
  { path: "accommodation/create", component: AccommodationCreateComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
