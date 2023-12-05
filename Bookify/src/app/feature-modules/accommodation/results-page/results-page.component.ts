import {Component, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";

@Component({
  selector: 'app-results-page',
  templateUrl: './results-page.component.html',
  styleUrl: './results-page.component.css'
})
export class ResultsPageComponent implements OnInit{
  accommodationModels: AccommodationBasicModel[]
  search: string;
  persons: number;
  dateBegin: Date;
  dateEnd: Date;

  constructor(private accommodationService: AccommodationService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.search = <string>this.route.snapshot.paramMap.get('search');
    this.persons = Number(this.route.snapshot.paramMap.get('persons'));
    this.dateBegin = new Date(Date.parse(<string>this.route.snapshot.paramMap.get('date-begin')));
    this.dateEnd = new Date(Date.parse(<string>this.route.snapshot.paramMap.get('date-end')));

    this.accommodationService.getForSearch(this.search, this.dateBegin, this.dateEnd, this.persons).subscribe({
      next: (data) => {
        this.accommodationModels = data;
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }

  handleButtonPress(values: { search: string; persons: number, dateBegin: string, dateEnd: string}): void {
    this.search = values.search;
    this.persons = values.persons;
    this.dateBegin = new Date(Date.parse(values.dateBegin));
    this.dateEnd = new Date(Date.parse(values.dateEnd));

    this.accommodationService.getForSearch(this.search, this.dateBegin, this.dateEnd, this.persons).subscribe({
      next: (data) => {
        this.accommodationModels = data;
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }
}
