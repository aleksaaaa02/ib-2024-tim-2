import {Component, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";

@Component({
  selector: 'app-results-page',
  templateUrl: './results-page.component.html',
  styleUrl: './results-page.component.css'
})
export class ResultsPageComponent implements OnInit{
  accommodationModels: AccommodationBasicModel[] = [];

  constructor(private accommodationService: AccommodationService) {}

  ngOnInit(): void {
    this.accommodationService.getForSearch('location', new Date(), new Date(), 2).subscribe({
      next: (data: AccommodationBasicModel[]) => {
        this.accommodationModels = data;
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }
}
