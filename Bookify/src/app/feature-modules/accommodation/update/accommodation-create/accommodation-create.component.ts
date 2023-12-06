import { Component, ViewChild } from '@angular/core';
import { AccommodationService } from '../../accommodation.service';
import { Router } from '@angular/router';
import { Address } from '../../model/address.dto.model';
import { AccommodationDTO } from '../../model/accommodation.dto.model';
import { Accommodation } from '../../model/accommodation.model';
import { File } from 'buffer';
import { AccommodationBasicInformationComponent } from '../accommodation-basic-information/accommodation-basic-information.component';
import { MatSnackBar } from '@angular/material/snack-bar'

@Component({
  selector: 'app-accommodation-create',
  templateUrl: './accommodation-create.component.html',
  styleUrl: './accommodation-create.component.css'
})
export class AccommodationCreateComponent {
  @ViewChild(AccommodationBasicInformationComponent) basicInfo!: AccommodationBasicInformationComponent;

  basicInfoPropertyName: string = '';
  basicInfoDescription: string = '';
  locationCountry: string = '';
  locationCity: string = '';
  locationStreetAddress: string = '';
  locationZipCode: string = '';
  amenitiesFilter: string[] = [];
  images: string[] = [];
  type: string = '';
  minGuests: number = 0;
  maxGuests: number = 0;
  reservationAcceptance: string = '';
  cancellationDeadline: number = 0;
  pricePer: string = '';

  constructor(private accommodationService: AccommodationService, private router: Router, private _snackBar: MatSnackBar) { }

  openSnackBar(message: string, action: string){
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

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
    this.images = data;

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

    if (this.isValid()) {
      const addressDTO: Address = {
        country: this.locationCountry,
        city: this.locationCity,
        address: this.locationStreetAddress,
        zipCode: this.locationZipCode
      }
      const dto: AccommodationDTO = {
        name: this.basicInfoPropertyName,
        description: this.basicInfoDescription,
        filters: this.amenitiesFilter,
        accommodationType: this.type === '' ? null : this.type,
        minGuest: this.minGuests,
        maxGuest: this.maxGuests,
        manual: this.reservationAcceptance === 'manual',
        cancellationDeadline: this.cancellationDeadline,
        pricePer: this.pricePer === '' ? null : this.pricePer,
        address: addressDTO
      };
      this.accommodationService.add(dto).subscribe(
        {
          next: (data: Accommodation) => {
            console.log(data);
            this.accommodationService.addImages(data.id, this.images).subscribe();
          },
          error: (_) => { }
        });
    } else {
      this.openSnackBar('All field must be filled', 'Close');
    }
  }

  isValid(): boolean {
    return this.locationCountry !== '' && this.locationCity !== '' && this.locationStreetAddress !== ''
      && this.locationZipCode !== '' && this.basicInfoPropertyName !== '' && this.basicInfoDescription !== '' &&
      this.type !== '' && this.reservationAcceptance !== '' && this.pricePer !== '' && this.images.length > 0 &&
      this.minGuests >= 0 && this.minGuests !== null && this.maxGuests >= 0 && this.maxGuests !== null &&
      this.cancellationDeadline >= 0 && this.cancellationDeadline !== null && this.minGuests <= this.maxGuests;
  }
}
