import { Component } from '@angular/core';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {
  minPrice: number = 0;
  maxPrice: number = 10000;

  onSliderChange() {
    // Log the values in the console
    console.log('Min Price:', this.minPrice);
    console.log('Max Price:', this.maxPrice);
  }

  checkboxLabels: string[] = [
    'Free WiFi', 'Air conditioning', 'Terrace', 'Swimming pool', 'Bar', 'Sauna', 'Luggage storage',
    'Lunch', 'Airport shuttle', 'Wheelchair', 'Non-smoking', 'Free parking',
    'Family rooms', 'Garden', '24-hour front desk', 'Jacuzzi', 'Heating',
    'Breakfast', 'Dinner', 'Private bathroom', 'Deposit box', 'City center'
  ];
}
