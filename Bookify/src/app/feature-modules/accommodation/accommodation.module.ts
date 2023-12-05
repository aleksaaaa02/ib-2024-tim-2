import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationBasicInformationComponent } from './update/accommodation-basic-information/accommodation-basic-information.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { AccommodationLocationComponent } from './update/accommodation-location/accommodation-location.component';
import { MatSelectModule } from '@angular/material/select';
import { AccommodationAmenitiesComponent } from './update/accommodation-amenities/accommodation-amenities.component';
import { AccommodationPhotosComponent } from './update/accommodation-photos/accommodation-photos.component';

@NgModule({
  declarations: [
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent,
    AccommodationAmenitiesComponent,
    AccommodationPhotosComponent
  ],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatSelectModule
  ]
})
export class AccommodationModule { }
