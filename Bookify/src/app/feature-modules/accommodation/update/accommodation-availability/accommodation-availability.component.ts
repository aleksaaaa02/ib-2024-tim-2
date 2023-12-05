import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-accommodation-availability',
  templateUrl: './accommodation-availability.component.html',
  styleUrl: './accommodation-availability.component.css'
})
export class AccommodationAvailabilityComponent {
  @Output() availibilityChanged = new EventEmitter<any>();
  cancellationDeadline: number = 0;
  pricePer: string = '';
  
  onAvailabilityChanged() {
    this.availibilityChanged.emit({
      cancellationDeadline: this.cancellationDeadline,
      pricePer: this.pricePer
    });
  }
}
