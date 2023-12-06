import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgControl } from '@angular/forms';

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

  constructor(){}

  create() {
    this.createAvailabilityForm.valid
  }
}
