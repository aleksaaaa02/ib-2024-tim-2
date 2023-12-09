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
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { CalendarComponent } from './update/calendar/calendar.component';

@NgModule({
  declarations: [
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent,
    AccommodationAmenitiesComponent,
    AccommodationPhotosComponent,
    AccommodationGuestsComponent,
    AccommodationAvailabilityComponent,
    AccommodationCreateComponent,
    CalendarComponent
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
    MatPaginatorModule
  ],
  exports:[
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent,
    AccommodationAmenitiesComponent,
    AccommodationPhotosComponent,
    AccommodationGuestsComponent,
    AccommodationAvailabilityComponent,
    AccommodationCreateComponent,
    CalendarComponent
  ]
})
export class AccommodationModule { }
