import {Component, Input} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";

@Component({
  selector: 'app-accommodation-basic',
  templateUrl: './accommodation-basic.component.html',
  styleUrl: './accommodation-basic.component.css'
})
export class AccommodationBasicComponent {

  @Input()
  basicAccommodation: AccommodationBasicModel

  constructor() {
  }

  calculatePercent(): string {
    return `calc(${this.basicAccommodation.avgRating}/5*100%)`;
  }
}
