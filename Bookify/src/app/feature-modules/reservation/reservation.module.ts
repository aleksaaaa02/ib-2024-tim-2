import { NgModule, SchemaMetadata } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationCardComponent } from './reservation-card/reservation-card.component';
import { RequestCardComponent } from './request-card/request-card.component';
import { OwnerReservationsComponent } from './owner-reservations/owner-reservations.component';
import { FilterReservationsComponent } from './filter-reservations/filter-reservations.component';
import { LayoutModule } from '../../layout/layout.module';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  declarations: [
    ReservationCardComponent,
    RequestCardComponent,
    OwnerReservationsComponent,
    FilterReservationsComponent
  ],
  imports: [
    CommonModule,
    LayoutModule,
    MatInputModule,
    FormsModule,
    MatSelectModule
  ],
  exports:[
    RequestCardComponent,
    ReservationCardComponent,
    OwnerReservationsComponent
  ]
})
export class ReservationModule { }
