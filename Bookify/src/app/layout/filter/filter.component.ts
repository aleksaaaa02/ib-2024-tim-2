import {Component, EventEmitter, Output} from '@angular/core';
import {FilterDTO} from "../../feature-modules/accommodation/model/filter.dto.model";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {
  minPrice: number = 0;
  maxPrice: number = 10000;
  minPossiblePrice: number = 0;
  maxPossiblePrice: number = 10000;

  @Output() buttonPressed= new EventEmitter<FilterDTO>();

  onButtonPress(): void {
    let filter: FilterDTO = {
      filters: [],
      types: [],
      minPrice: this.minPrice,
      maxPrice: this.maxPrice
    }
    this.buttonPressed.emit(filter);
  }

  checkboxLabels: string[] = [
    'Free WiFi', 'Air conditioning', 'Terrace', 'Swimming pool', 'Bar', 'Sauna', 'Luggage storage',
    'Lunch', 'Airport shuttle', 'Wheelchair', 'Non-smoking', 'Free parking',
    'Family rooms', 'Garden', '24-hour front desk', 'Jacuzzi', 'Heating',
    'Breakfast', 'Diner', 'Private bathroom', 'Deposit box', 'City center'
  ];
}
