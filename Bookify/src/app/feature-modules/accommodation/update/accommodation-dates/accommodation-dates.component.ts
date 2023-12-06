import { Component, EventEmitter, Output } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { AccommodationService } from '../../accommodation.service';
import { Router } from '@angular/router';
import { PriceList } from '../../model/priceList.dto';

@Component({
  selector: 'app-accommodation-dates',
  templateUrl: './accommodation-dates.component.html',
  styleUrl: './accommodation-dates.component.css'
})
export class AccommodationDatesComponent {
  @Output() priceListUpdate = new EventEmitter<null>();

  createAvailabilityForm!: FormGroup;
  //  new FormGroup({
  //   checkIn: new FormControl(),
  //   checkOut: new FormControl(),
  //   price: new FormControl(),
  // });

  constructor(private accommodationService: AccommodationService, private router: Router, private fb: FormBuilder) {
    this.createAvailabilityForm = this.fb.group({
      checkIn: ['', [Validators.required, this.dateValidator]],
      checkOut: ['', [Validators.required, this.dateValidator]],
      price: ['', [Validators.required, Validators.min(0)]],
    }, { validators: dateComparisonValidator() });
  }

  get dateValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const currentDate = new Date();
      const selectedDate = new Date(control.value);
  
      if (selectedDate < currentDate) {
        return { datePast: true, message: 'Selected date cannot be before today' };
      }
  
      return null;
    };
  }

  create() {
    if (this.createAvailabilityForm.valid) {
      console.log("PROSLO");
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

        // this.accommodationService.addPriceList(1, priceList).subscribe(
        //   {
        //     next: (data) => {
        //       this.priceListUpdate.emit()
        //     },
        //     error: (_) => { }
        //   }
        // )
      }
    }else{
      console.log("NIJE PROSLO");
    }
  }
}

export function dateComparisonValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const checkInControl = control.get('checkIn');
    const checkOutControl = control.get('checkOut');
    if(checkInControl && checkOutControl){
      const checkInDate = checkInControl.value;
      const checkOutDate = checkOutControl.value;
      
      if (checkInDate && checkOutDate && new Date(checkOutDate) < new Date(checkInDate)) {
        return { dateComparison: true, message: 'Check out date cannot be before check in date' };
      }
      
    }
    return null;
  };
}