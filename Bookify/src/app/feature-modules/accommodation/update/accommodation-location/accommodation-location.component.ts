import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-accommodation-location',
  templateUrl: './accommodation-location.component.html',
  styleUrl: './accommodation-location.component.css'
})
export class AccommodationLocationComponent {
  @Output() locationChanged = new EventEmitter<any>();
  country: string = '';
  city: string = '';
  streetAddress: string = '';
  zipCode: string = '';

  countries: Promise<string[]> = Promise.resolve([]);

  // constructor(private authenticationService: AuthenticationService) {}
  
  ngOnInit(): void {
    // this.countries = this.authenticationService.getCountries();
  }

  onLocationChange() {
    this.locationChanged.emit({
      country: this.country,
      city: this.city,
      streetAddress: this.streetAddress,
      zipCode: this.zipCode
    });
  }
}
