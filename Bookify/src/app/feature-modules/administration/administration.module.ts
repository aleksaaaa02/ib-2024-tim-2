import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationRequestsComponent } from './accommodation-requests/accommodation-requests.component';
import { MaterialModule } from "../../infrastructure/material/material.module";
import { AccommodationRequestsCardComponent } from './accommodation-requests-card/accommodation-requests-card.component';
import {CreatedPipe} from "./pipes/created";
import {EditedPipe} from "./pipes/edited";
import {RouterLink} from "@angular/router";

@NgModule({
  declarations: [
    AccommodationRequestsComponent,
    AccommodationRequestsCardComponent,
    EditedPipe,
    CreatedPipe
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterLink
  ],
  exports: [
    AccommodationRequestsComponent
  ]
})
export class AdministrationModule { }
