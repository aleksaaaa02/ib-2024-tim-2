import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AccommodationService } from '../../accommodation.service';
import { Router } from '@angular/router';
import { PriceList } from '../../model/priceList.dto';

@Component({
  selector: 'app-accommodation-dates',
  templateUrl: './accommodation-dates.component.html',
  styleUrl: './accommodation-dates.component.css'
})
export class AccommodationDatesComponent {
  createAvailabilityForm = new FormGroup({
    checkIn: new FormControl(),
    checkOut: new FormControl(),
    price: new FormControl(),
  });

  constructor(private accommodationService: AccommodationService, private router: Router) { }

  create() {
    if (this.createAvailabilityForm.valid) {
      const checkInControl = this.createAvailabilityForm.get('checkIn');
      const checkOutControl = this.createAvailabilityForm.get('checkOut');
      const priceControl = this.createAvailabilityForm.get('price');

      if (checkInControl && checkOutControl && priceControl) {
        const checkInValue = checkInControl.value;
        const checkOutValue = checkOutControl.value;
        const priceValue = priceControl.value;
        // Create PriceList object
        const priceList: PriceList = {
          startDate: new Date(checkInValue),
          endDate: new Date(checkOutValue),
          price: priceValue
        };

        console.log(priceList);
        this.accommodationService.addPriceList(1, priceList).subscribe(
          // {
          //   next: (data) => {

          //   },
          //   error: (_) => {}
          // }
        )
      }
    }
  }
}