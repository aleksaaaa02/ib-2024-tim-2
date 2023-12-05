import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationBasicInformationComponent } from './accommodation-basic-information/accommodation-basic-information.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { AccommodationLocationComponent } from './accommodation-location/accommodation-location.component';
import { MatSelectModule } from '@angular/material/select';
import { AccommodationAmenitiesComponent } from './accommodation-amenities/accommodation-amenities.component';
import { AccommodationPhotosComponent } from './accommodation-photos/accommodation-photos.component';
import { AccommodationGuestsComponent } from './accommodation-guests/accommodation-guests.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { AccommodationAvailabilityComponent } from './accommodation-availability/accommodation-availability.component';
import { AccommodationCreateComponent } from './accommodation-create/accommodation-create.component';

@NgModule({
  declarations: [
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent,
    AccommodationAmenitiesComponent,
    AccommodationPhotosComponent,
    AccommodationGuestsComponent,
    AccommodationAvailabilityComponent,
    AccommodationCreateComponent
  ],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatSelectModule,
    MatButtonToggleModule
  ],
  exports:[
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent,
    AccommodationAmenitiesComponent,
    AccommodationPhotosComponent,
    AccommodationGuestsComponent,
    AccommodationAvailabilityComponent,
    AccommodationCreateComponent
  ]
})
export class AccommodationModule { }
