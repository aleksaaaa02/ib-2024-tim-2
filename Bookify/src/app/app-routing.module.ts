import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from "./account/account/account.component";
import { ResultsPageComponent } from "./feature-modules/accommodation/results-page/results-page.component";
import { AccommodationPageComponent } from "./feature-modules/accommodation/accommodation-page/accommodation-page.component";
import { LandingPageComponent } from "./layout/landing-page/landing-page.component";
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
import { AccommodationDatesComponent } from './feature-modules/accommodation/update/accommodation-dates/accommodation-dates.component';
import { AccommodationPriceListItemsComponent } from './feature-modules/accommodation/update/accommodation-price-list-items/accommodation-price-list-items.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "confirmation", component: ConfirmationComponent },
  { path: "results", component: ResultsPageComponent },
  { path: "accommodation", component: AccommodationPageComponent },
  { path: '', component: LandingPageComponent },
  { path: "account", component: AccountComponent },
  { path: "accommodation/create/basic-info", component: AccommodationCreateComponent },
  { path: "accommodation/create/location", component: AccommodationLocationComponent },
  { path: "accommodation/create/amenities", component: AccommodationAmenitiesComponent },
  { path: "accommodation/create/photos", component: AccommodationPhotosComponent },
  { path: "accommodation/create/guests", component: AccommodationGuestsComponent },
  { path: "accommodation/create/availability", component: AccommodationAvailabilityComponent },
  { path: "accommodation/create", component: AccommodationCreateComponent },
  { path: "accommodation/create/dates", component: AccommodationDatesComponent },
  { path: "accommodation/create/price", component: AccommodationPriceListItemsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
