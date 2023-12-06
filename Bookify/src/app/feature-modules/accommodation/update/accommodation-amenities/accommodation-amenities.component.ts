import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-accommodation-amenities',
  templateUrl: './accommodation-amenities.component.html',
  styleUrl: './accommodation-amenities.component.css'
})
export class AccommodationAmenitiesComponent {
  @Output() amenitiesChange = new EventEmitter<string[]>();
  checkboxLabels: string[] = [
    'Free WiFi', 'Air conditioning', 'Terrace', 'Swimming pool', 'Bar', 'Sauna', 'Luggage storage',
    'Lunch', 'Airport shuttle', 'Wheelchair', 'Non-smoking', 'Free parking',
    'Family rooms', 'Garden', '24-hour front desk', 'Jacuzzi', 'Heating',
    'Breakfast', 'Diner', 'Private bathroom', 'Deposit box', 'City center'
  ];
  checkedCheckboxes: string[] = [];

  onCheckboxChange(label: string) {
    if (this.checkedCheckboxes.includes(label)) {
      // Remove the label if it's already in the array (unchecked)
      this.checkedCheckboxes = this.checkedCheckboxes.filter(item => item !== label);
    } else {
      // Add the label if it's not in the array (checked)
      this.checkedCheckboxes.push(label);
    }
    this.amenitiesChange.emit(this.checkedCheckboxes);
  }

  trackByFn(index: number, item: string): number {
    return index; // or return the unique identifier of your items if available
  }
}
