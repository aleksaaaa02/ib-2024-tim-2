import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { AccommodationService } from '../../accommodation.service';
import { Router } from '@angular/router';
import { PriceListDTO } from '../../model/priceList.dto.model';
import { PriceList } from '../../model/priceList.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-accommodation-dates',
  templateUrl: './accommodation-dates.component.html',
  styleUrl: './accommodation-dates.component.css'
})
export class AccommodationDatesComponent implements OnChanges {
  @Output() priceListUpdate = new EventEmitter<null>();
  @Input() priceList: PriceList;
  @Input() shouldEdit: boolean = false;
  
  createAvailabilityForm!: FormGroup;
  //  new FormGroup({
  //   checkIn: new FormControl(),
  //   checkOut: new FormControl(),
  //   price: new FormControl(),
  // });
  
  constructor(private accommodationService: AccommodationService, private router: Router, private fb: FormBuilder, private datePipe: DatePipe) {
    this.createAvailabilityForm = this.fb.group({
      formattedStartDate: ['', [Validators.required, this.dateValidator]],
      formattedEndDate: ['', [Validators.required, this.dateValidator]],
      price: ['', [Validators.required, Validators.min(0)]],
    }, { validators: dateComparisonValidator() });
  }
  
  ngOnChanges(changes: SimpleChanges): void {
    console.log("CH");
    if (this.priceList && this.shouldEdit) {
      const checkIn = this.datePipe.transform(this.priceList.startDate, 'yyyy-MM-dd');
      const checkOut = this.datePipe.transform(this.priceList.endDate, 'yyyy-MM-dd');
      if (checkIn && checkOut) {
        this.priceList.formattedStartDate = checkIn;
        this.priceList.formattedEndDate = checkOut;
      }
      this.createAvailabilityForm.patchValue(this.priceList);
    }
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
  
  new() {
    console.log("AAA");
    this.shouldEdit = false;
    this.createAvailabilityForm.reset();
  }

  create() {
    if (this.createAvailabilityForm.valid) {
      const checkInControl = this.createAvailabilityForm.get('formattedStartDate');
      const checkOutControl = this.createAvailabilityForm.get('formattedEndDate');
      const priceControl = this.createAvailabilityForm.get('price');
      
      if (checkInControl && checkOutControl && priceControl) {
        console.log("PROSLO");
        const checkInValue = checkInControl.value;
        const checkOutValue = checkOutControl.value;
        const priceValue = priceControl.value;
        // Create PriceList object
        const priceList: PriceListDTO = {
          startDate: new Date(checkInValue),
          endDate: new Date(checkOutValue),
          price: priceValue
        };

        this.accommodationService.addPriceList(1, priceList).subscribe(
          {
            next: (data) => {
              this.priceListUpdate.emit()
            },
            error: (_) => { }
          }
        )
      }
    } else {
      console.log("NIJE PROSLO");
    }
  }

  update(): void {
    if (this.createAvailabilityForm.valid) {
      const checkInControl = this.createAvailabilityForm.get('formattedStartDate');
      const checkOutControl = this.createAvailabilityForm.get('formattedEndDate');
      const priceControl = this.createAvailabilityForm.get('price');

      if (checkInControl && checkOutControl && priceControl) {
        console.log("PROSLO2");
        const checkInValue = checkInControl.value;
        const checkOutValue = checkOutControl.value;
        const priceValue = priceControl.value;
        // Create PriceList object
        const priceList: PriceListDTO = {
          startDate: new Date(checkInValue),
          endDate: new Date(checkOutValue),
          price: priceValue
        };

        this.accommodationService.updatePriceList(this.priceList.id, priceList).subscribe(
          {
            next: (data) => {
              this.priceListUpdate.emit()
            },
            error: (_) => { }
          }
        )
      }
    } else {
      console.log("NIJE PROSLO2");
    }
  }

}

export function dateComparisonValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const checkInControl = control.get('formattedStartDate');
    const checkOutControl = control.get('formattedEndDate');
    if (checkInControl && checkOutControl) {
      const checkInDate = checkInControl.value;
      const checkOutDate = checkOutControl.value;

      if (checkInDate && checkOutDate && new Date(checkOutDate) < new Date(checkInDate)) {
        return { dateComparison: true, message: 'Check out date cannot be before check in date' };
      }

    }
    return null;
  };
}