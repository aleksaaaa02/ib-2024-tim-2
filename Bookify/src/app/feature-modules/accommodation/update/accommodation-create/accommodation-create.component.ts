import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { AccommodationService } from '../../accommodation.service';
import { Router } from '@angular/router';
import { Address } from '../../model/address.dto.model';
import { AccommodationDTO } from '../../model/accommodation.dto.model';
import { Accommodation } from '../../model/accommodation.model';
import { AccommodationBasicInformationComponent } from '../accommodation-basic-information/accommodation-basic-information.component';
import { MatSnackBar } from '@angular/material/snack-bar'
import { AccommodationBasicFormModel } from '../../model/accommodation-basic.form.model';
import { AccommodationGuestsFormModel } from '../../model/accommodation-guests.form.model';
import { ImagesDTO } from '../../model/images';

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
  images: ImagesDTO[] = [];
  f: File[] = [];
  type: string = '';
  minGuests: number = 0;
  maxGuests: number = 0;
  reservationAcceptance: string = '';
  cancellationDeadline: number = 0;
  pricePer: string = '';
  submitted: boolean = false;

  constructor(private accommodationService: AccommodationService, private router: Router, private _snackBar: MatSnackBar) { }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  handleBasicInfoChange(data: AccommodationBasicFormModel) {
    this.basicInfoPropertyName = data.propertyName;
    this.basicInfoDescription = data.description;
  }

  handleLocationChange(data: Address) {
    this.locationCountry = data.country;
    this.locationCity = data.city;
    this.locationStreetAddress = data.address;
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

  handlePhotosChange(data: ImagesDTO[]) {
    this.images = data;
  }

  handleGuestsChange(data: AccommodationGuestsFormModel) {
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
    console.log(this.images);
    this.submitted = true;
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
      //owner id
      this.accommodationService.add(3, dto).subscribe(
        {
          next: (data: Accommodation) => {
            this.images.forEach((elem) => {
              this.f.push(elem.file);
            })
            this.accommodationService.addImages(data.id, this.f).subscribe({
              next: () => {
                this.router.navigate(['/accommodation/calendar/', data.id]);
              }
            });
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
