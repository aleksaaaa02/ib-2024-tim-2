import { Component, EventEmitter, Output } from '@angular/core';
import { AccommodationService } from '../../accommodation.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-accommodation-location',
  templateUrl: './accommodation-location.component.html',
  styleUrl: './accommodation-location.component.css'
})
export class AccommodationLocationComponent {
  @Output() locationChanged = new EventEmitter<any>();
  @Output() validationStatus: EventEmitter<boolean> = new EventEmitter<boolean>();

  form: FormGroup;
  country: string = 'Serbia';
  city: string = '';
  streetAddress: string = '';
  zipCode: string = '';

  countries: Promise<string[]> = Promise.resolve([]);
  countryTouched: boolean = false;

  constructor(private accommodationService: AccommodationService, private fb: FormBuilder) {
    this.form = this.fb.group({
      // country: ['Serbia', Validators.required],
      city: ['', Validators.required],
      streetAddress: ['', Validators.required],
      zipCode: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.countries = this.accommodationService.getCountries();
  }

  clicked() {
    this.countryTouched = true;
  }

  onLocationChange() {
    this.countryTouched = true;
    this.locationChanged.emit({
      country: this.country,
      city: this.city,
      streetAddress: this.streetAddress,
      zipCode: this.zipCode
    });
  }

  validateForm() {
    const isValid = this.city.trim().length > 0 && this.streetAddress.trim().length > 0 && this.zipCode.trim().length > 0;
    this.validationStatus.emit(isValid);
    return isValid;
  }
}
