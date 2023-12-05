import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-accommodation-basic-information',
  templateUrl: './accommodation-basic-information.component.html',
  styleUrl: './accommodation-basic-information.component.css'
})
export class AccommodationBasicInformationComponent {
  @Output() basicInfoChanged = new EventEmitter<any>();
  propertyName: string = '';
  description: string = '';

  onBasicInfoChange() {
    this.basicInfoChanged.emit({
      propertyName: this.propertyName,
      description: this.description,
    });
  }
}
