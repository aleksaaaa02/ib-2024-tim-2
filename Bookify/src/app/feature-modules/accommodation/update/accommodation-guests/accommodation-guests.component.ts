import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-accommodation-guests',
  templateUrl: './accommodation-guests.component.html',
  styleUrl: './accommodation-guests.component.css'
})
export class AccommodationGuestsComponent {
  @Output() guestsChanged = new EventEmitter<any>();
  type: string = '';
  minGuests: number = 0;
  maxGuests: number = 0;
  reservationAcceptance: string = '';

  onGuestsChange() {
    this.guestsChanged.emit({
      type: this.type,
      minGuests: this.minGuests,
      maxGuests: this.maxGuests,
      reservationAcceptance: this.reservationAcceptance
    });
  }
}
