import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationBasicInformationComponent } from './update/accommodation-basic-information/accommodation-basic-information.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AccommodationBasicInformationComponent
  ],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule
  ]
})
export class AccommodationModule { }
