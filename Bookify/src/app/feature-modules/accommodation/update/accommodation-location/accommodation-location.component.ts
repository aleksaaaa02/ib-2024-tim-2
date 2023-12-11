import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { AccommodationService } from '../../accommodation.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Address } from '../../model/address.dto.model';

@Component({
  selector: 'app-accommodation-location',
  templateUrl: './accommodation-location.component.html',
  styleUrl: './accommodation-location.component.css'
})
export class AccommodationLocationComponent implements OnChanges {
  @Output() locationChanged = new EventEmitter<Address>();
  @Output() validationStatus: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() submitted: boolean = false;

  form: FormGroup;

  locationAddress: string = "";

  countries: Promise<string[]> = Promise.resolve([]);
  countryTouched: boolean = false;
  location: string;
  create: boolean = true;

  constructor(private accommodationService: AccommodationService, private fb: FormBuilder) {
    this.form = this.fb.group({
      country: ['', Validators.required],
      city: ['', Validators.required],
      streetAddress: ['', Validators.required],
      zipCode: ['', Validators.required],
    });
    this.form.valueChanges.subscribe(value => {
      this.onLocationChange();
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.submitted) {
      this.form.markAllAsTouched();
    }
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
      country: this.form.get('country')?.value,
      city: this.form.get('city')?.value,
      address: this.form.get('streetAddress')?.value,
      zipCode: this.form.get('zipCode')?.value
    });
    this.locationAddress = this.form.get('streetAddress')?.value + " " + this.form.get('city')?.value + " " + this.form.get('zipCode')?.value + " " + this.form.get('country')?.value;
  }

  handleLocationClick(data: string[]) {
    this.form.get('country')?.setValue(data[0]);
    this.form.get('city')?.setValue(data[1]);
    this.form.get('streetAddress')?.setValue(data[2] + " " + data[4]);
    this.form.get('zipCode')?.setValue(data[3]);
  }

  validateForm() {
    const isValid = this.form.get('city')?.value.trim().length > 0 && this.form.get('streetAddress')?.value.trim().length > 0 && this.form.get('zipCode')?.value.trim().length > 0;
    this.validationStatus.emit(isValid);
    return isValid;
  }
}
