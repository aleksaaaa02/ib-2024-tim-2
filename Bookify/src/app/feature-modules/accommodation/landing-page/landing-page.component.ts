import {Component, OnInit} from '@angular/core';
import {AccommodationService} from "../accommodation.service";
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent implements OnInit{
  search: string;
  persons: string;
  dateBegin: string;
  dateEnd: string;

  constructor(private accommodationService: AccommodationService, private router: Router) {}

  ngOnInit(): void {
  }
  handleButtonPress(values: { search: string; persons: number, dateBegin: string, dateEnd: string}): void {
    this.search = values.search;
    this.persons = values.persons.toString();
    this.dateBegin = values.dateBegin;
    this.dateEnd = values.dateEnd;

    this.router.navigate(['/results', this.search, this.persons, this.dateBegin, this.dateEnd]);
  }
}
