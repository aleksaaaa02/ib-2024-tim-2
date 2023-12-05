import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationBasicInformationComponent } from './update/accommodation-basic-information/accommodation-basic-information.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { AccommodationLocationComponent } from './update/accommodation-location/accommodation-location.component';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  declarations: [
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent
  ],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatSelectModule
  ]
})
export class AccommodationModule { }
