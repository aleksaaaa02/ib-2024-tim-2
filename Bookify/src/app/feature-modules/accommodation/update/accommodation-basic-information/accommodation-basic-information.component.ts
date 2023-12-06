import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-accommodation-basic-information',
  templateUrl: './accommodation-basic-information.component.html',
  styleUrl: './accommodation-basic-information.component.css'
})
export class AccommodationBasicInformationComponent {
  @Output() basicInfoChanged = new EventEmitter<any>();
  @Output() validationStatus: EventEmitter<boolean> = new EventEmitter<boolean>();

  form: FormGroup;
  propertyName: string = '';
  description: string = '';

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      propertyName: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  onBasicInfoChange() {
    this.basicInfoChanged.emit({
      propertyName: this.propertyName,
      description: this.description,
    });
  }

  validateForm() {
    const isValid = this.propertyName.trim().length > 0 && this.description.trim().length > 0;
    this.validationStatus.emit(isValid);
    return isValid;
  }
}
