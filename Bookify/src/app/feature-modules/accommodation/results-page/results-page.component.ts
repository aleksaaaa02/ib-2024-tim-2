import {Component, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";
import {ActivatedRoute, Router} from "@angular/router";
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
  currentPage = 1;
  pageSize = 10;

  constructor(private accommodationService: AccommodationService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.search = <string>this.route.snapshot.params['search'];
    this.persons = Number(this.route.snapshot.params['persons']);
    this.dateBegin = new Date(Date.parse(<string>this.route.snapshot.params['begin']));
    this.dateEnd = new Date(Date.parse(<string>this.route.snapshot.params['end']));

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
    this.accommodationService.getForSearch(this.search, this.dateBegin, this.dateEnd, this.persons).subscribe({
      next: (data) => {
        this.accommodationModels = data;
        this.router.navigate(['/results', {"search": values.search, "persons": values.persons, "begin": values.dateBegin, "end": values.dateEnd}]);
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }
}
