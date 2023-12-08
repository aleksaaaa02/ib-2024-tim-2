import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationBasicInformationComponent } from './update/accommodation-basic-information/accommodation-basic-information.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AccommodationLocationComponent } from './update/accommodation-location/accommodation-location.component';
import { MatSelectModule } from '@angular/material/select';
import { AccommodationAmenitiesComponent } from './update/accommodation-amenities/accommodation-amenities.component';
import { AccommodationPhotosComponent } from './update/accommodation-photos/accommodation-photos.component';
import { AccommodationGuestsComponent } from './update/accommodation-guests/accommodation-guests.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { AccommodationAvailabilityComponent } from './update/accommodation-availability/accommodation-availability.component';
import { AccommodationCreateComponent } from './update/accommodation-create/accommodation-create.component';
import { AccommodationDatesComponent } from './update/accommodation-dates/accommodation-dates.component';
import { AccommodationPriceListItemsComponent } from './update/accommodation-price-list-items/accommodation-price-list-items.component';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent,
    AccommodationAmenitiesComponent,
    AccommodationPhotosComponent,
    AccommodationGuestsComponent,
    AccommodationAvailabilityComponent,
    AccommodationCreateComponent,
    AccommodationDatesComponent,
    AccommodationPriceListItemsComponent
  ],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatSelectModule,
    MatButtonToggleModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    SharedModule
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
