import {Inject, Injectable, LOCALE_ID } from '@angular/core';
import {AccommodationBasicModel} from "./model/accommodation-basic.model";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {environment} from "../../../env/env";
import moment from 'moment';
import { AccommodationDTO } from './model/accommodation.dto.model';
import { Accommodation } from './model/accommodation.model';
import {FilterDTO} from "./model/filter.dto.model";

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  private basicAccommodationList: AccommodationBasicModel[] = [];
  private accommodations: AccommodationDTO[] = [];

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getForSearch(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number): Observable<AccommodationBasicModel[]> {
    return this.httpClient.get<AccommodationBasicModel[]>(environment.apiHost + 'accommodations/' +
                                                                                    "search?location=" + location +
                                                                                    "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
                                                                                    "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
                                                                                    "&persons=" + persons +
                                                                                    "&page=" + page +
                                                                                    "&size=" + size);
  }

  getForFilterAndSort(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number, sort: string, filter: FilterDTO): Observable<AccommodationBasicModel[]> {
    return this.httpClient.post<AccommodationBasicModel[]>(environment.apiHost + 'accommodations/' +
                                                                                    "filter?location=" + location +
                                                                                    "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
                                                                                    "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
                                                                                    "&persons=" + persons +
                                                                                    "&page=" + page +
                                                                                    "&size=" + size +
                                                                                    "&sort=" + sort, filter);
  }

  getCountForSearch(location: string, dateBegin: Date, dateEnd: Date, persons: number): Observable<number> {
    return this.httpClient.get<number>(environment.apiHost + 'accommodations/' +
                                                                  "search-count?location=" + location +
                                                                  "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
                                                                  "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
                                                                  "&persons=" + persons);
  }

  getImage(imageId: number) : Observable<Blob> {
    return this.httpClient.get(environment.apiHost + "accommodations/images/" + imageId, {responseType: 'blob'});
  }

  async getCountries(): Promise<string[]> {
      try {
        const response = await fetch('https://restcountries.com/v3.1/all');
        const countriesData = await response.json();
        const countries = countriesData.map((country: any) => country.name.common);
        countries.sort();
        return countries;
      } catch (error) {
        console.error('Error fetching countries:', error);
        return [];
      }
    }

  add(accommodation: AccommodationDTO): Observable<Accommodation> {
    return this.httpClient.post<Accommodation>(environment.apiAccommodation, accommodation)
  }

  addImages(accommodationId: number, images: string[]) {
    const data: FormData = new FormData();
    images.forEach((element: string) => {
      let blob: Blob = new Blob([element], { type: "text/plain" });
      let file: File = new File([blob], "sss");
      data.append("images", file);
    })
    console.log(images);
    return this.httpClient.post<string[]>(environment.apiAccommodation + "/" + accommodationId, data);
  }
}
