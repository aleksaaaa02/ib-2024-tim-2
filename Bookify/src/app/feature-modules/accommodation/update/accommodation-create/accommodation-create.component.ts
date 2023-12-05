import { Component } from '@angular/core';
import { AccommodationService } from '../../accommodation.service';
import { Router } from '@angular/router';
import { Address } from '../../model/address.dto.model';
import { Accommodation } from '../../model/accommodation.dto.model';

@Component({
  selector: 'app-accommodation-create',
  templateUrl: './accommodation-create.component.html',
  styleUrl: './accommodation-create.component.css'
})
export class AccommodationCreateComponent {
  basicInfoPropertyName: string = '';
  basicInfoDescription: string = '';
  locationCountry: string = '';
  locationCity: string = '';
  locationStreetAddress: string = '';
  locationZipCode: string = '';
  amenitiesFilter: string[] = [];
  photos: string[] = [];
  type: string = '';
  minGuests: number = 0;
  maxGuests: number = 0;
  reservationAcceptance: string = '';
  cancellationDeadline: number = 0;
  pricePer: string = '';

  constructor(private accommodationService: AccommodationService, private router: Router) { }

  handleBasicInfoChange(data: any) {
    this.basicInfoPropertyName = data.propertyName;
    this.basicInfoDescription = data.description;
  }

  handleLocationChange(data: any) {
    this.locationCountry = data.country;
    this.locationCity = data.city;
    this.locationStreetAddress = data.streetAddress;
    this.locationZipCode = data.zipCode;
  }

  handleAmenitiesChange(data: string[]) {
    this.amenitiesFilter = data.map(label => this.transformLabel(label));
  }

  transformLabel(label: string): string {
    if (label === '24-hour front desk') {
      return 'FRONT_DESK';
    }
    return label.toUpperCase().replace(/[^A-Z]/g, '_');
  }

  handlePhotosChange(data: string[]) {
    this.photos = data;
  }

  handleGuestsChange(data: any) {
    this.type = data.type;
    this.minGuests = data.minGuests;
    this.maxGuests = data.maxGuests;
    this.reservationAcceptance = data.reservationAcceptance;
  }

  handleAvailabilityChange(data: any) {
    this.cancellationDeadline = data.cancellationDeadline;
    this.pricePer = data.pricePer;
  }

  onSubmit() {
    const addressDTO: Address = {
      country: this.locationCountry,
      city: this.locationCity,
      address: this.locationStreetAddress,
      zipCode: this.locationZipCode
    }
    const dto: Accommodation = {
      name: this.basicInfoPropertyName,
      description: this.basicInfoDescription,
      filters: this.amenitiesFilter,
      // photos: this.photos,
      accommodationType: this.type === '' ? null : this.type,
      minGuest: this.minGuests,
      maxGuest: this.maxGuests,
      manual: this.reservationAcceptance === 'manual',
      cancellationDeadline: this.cancellationDeadline,
      pricePer: this.pricePer === '' ? null : this.pricePer,
      address: addressDTO
    };
    this.accommodationService.add(dto).subscribe();
  }
}
