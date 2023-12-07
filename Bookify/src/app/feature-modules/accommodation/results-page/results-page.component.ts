import {ChangeDetectionStrategy, Component, OnInit, ViewChild} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {FilterDTO} from "../model/filter.dto.model";

@Component({
  selector: 'app-results-page',
  templateUrl: './results-page.component.html',
  styleUrl: './results-page.component.css',
  changeDetection: ChangeDetectionStrategy.Default
})
export class ResultsPageComponent implements OnInit{
  @ViewChild(MatPaginator) paginator: MatPaginator;
  accommodationModels: AccommodationBasicModel[]
  search: string;
  persons: number;
  dateBegin: Date;
  dateEnd: Date;
  currentPage = 1;
  pageSize = 5;
  allResults: number;
  sort: string;

  constructor(private accommodationService: AccommodationService, private route: ActivatedRoute, private router: Router) {}

  onSortChange() {
    this.currentPage = 1;
    this.paginator.pageIndex = 0;
    this.getSortAndFilterResults();
  }

  getSortAndFilterResults(){
    let filter: FilterDTO = {
      filters: [],
      types: [],
      minPrice: 0,
      maxPrice: 0
    }
    this.accommodationService.getForFilterAndSort(this.search, this.dateBegin, this.dateEnd, this.persons, this.currentPage-1, this.pageSize, this.sort, filter).subscribe({
      next: (data) => {
        this.accommodationModels = data;
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }

  resultCount() {
    this.accommodationService.getCountForSearch(this.search, this.dateBegin, this.dateEnd, this.persons).subscribe({
      next: (data) => {
        this.allResults = data;
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    })
  }

  getResults() {
    this.accommodationService.getForSearch(this.search, this.dateBegin, this.dateEnd, this.persons, this.currentPage-1, this.pageSize).subscribe({
      next: (data) => {
        this.accommodationModels = data;
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }

  onPageChange(event: PageEvent) {
    if (this.pageSize != event.pageSize) {
      this.pageSize = event.pageSize;
      this.paginator.pageIndex = 0;
    }
    if (this.currentPage != event.pageIndex + 1) {
      this.currentPage = event.pageIndex + 1;

      window.scrollTo({
        top: 1000,
        behavior: 'smooth'
      });
    }
    const array: Array<string> = ['Name', 'Lowest', 'Highest'];
    if (array.indexOf(this.sort) != -1)
      this.getSortAndFilterResults();
    else
      this.getResults();
  }

  ngOnInit(): void {
    this.search = <string>this.route.snapshot.params['search'];
    this.persons = Number(this.route.snapshot.params['persons']);
    this.dateBegin = new Date(Date.parse(<string>this.route.snapshot.params['begin']));
    this.dateEnd = new Date(Date.parse(<string>this.route.snapshot.params['end']));

    this.resultCount();
    this.getResults();
  }

  handleButtonPress(values: { search: string; persons: number, dateBegin: string, dateEnd: string}): void {
    this.persons = values.persons;
    this.search = values.search;
    this.dateBegin = new Date(Date.parse(values.dateBegin));
    this.dateEnd = new Date(Date.parse(values.dateEnd));

    this.paginator.pageIndex = 0;
    this.currentPage = 1;

    this.resultCount();
    this.getResults();

    this.router.navigate(['/results', {"search": values.search, "persons": values.persons, "begin": values.dateBegin, "end": values.dateEnd}]);
  }
}
