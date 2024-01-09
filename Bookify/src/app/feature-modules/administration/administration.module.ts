import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationRequestsComponent } from './accommodation-requests/accommodation-requests.component';
import { MaterialModule } from "../../infrastructure/material/material.module";
import { AccommodationRequestsCardComponent } from './accommodation-requests-card/accommodation-requests-card.component';
import {CreatedPipe} from "./pipes/created";
import {EditedPipe} from "./pipes/edited";
import {RouterLink} from "@angular/router";
import { UsersComponent } from './users/users.component';
import { UsersCardComponent } from './users-card/users-card.component';
import { ReportedUsersCardComponent } from './reported-users-card/reported-users-card.component';

@NgModule({
  declarations: [
    AccommodationRequestsComponent,
    AccommodationRequestsCardComponent,
    EditedPipe,
    CreatedPipe,
    UsersComponent,
    UsersCardComponent,
    ReportedUsersCardComponent
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
