import {Component, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
  selector: 'app-owner-accommodations',
  templateUrl: './owner-accommodations.component.html',
  styleUrl: './owner-accommodations.component.css'
})
export class OwnerAccommodationsComponent implements OnInit{
  accommodations: AccommodationBasicModel[];

  constructor(private accommodationService: AccommodationService,
              private authenticationService: AuthenticationService) {

  }

  ngOnInit(): void {
    this.accommodationService.getOwnerAccommodations(this.authenticationService.getUserId()).subscribe({
      next: (data: AccommodationBasicModel[]) => {
        this.accommodations = data;
      },
      error: err => {}
    });
  }

}
