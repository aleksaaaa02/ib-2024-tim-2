import {Component, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";

@Component({
  selector: 'app-owner-accommodations',
  templateUrl: './owner-accommodations.component.html',
  styleUrl: './owner-accommodations.component.css'
})
export class OwnerAccommodationsComponent implements OnInit{
  accommodations: AccommodationBasicModel[];

  constructor(private accommodationService: AccommodationService) {

  }

  ngOnInit(): void {
    this.accommodationService.getOwnerAccommodations(3).subscribe({
      next: (data: AccommodationBasicModel[]) => {
        this.accommodations = data;
      },
      error: err => {}
    });
  }
}
