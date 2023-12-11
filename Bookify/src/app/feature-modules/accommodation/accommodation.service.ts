import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { AccommodationBasicModel } from "./model/accommodation-basic.model";
import { HttpClient, HttpParams, HttpResponse } from "@angular/common/http";
import { map, Observable } from "rxjs";
import { environment } from "../../../env/env";
import moment from 'moment';
import { AccommodationDTO } from './model/accommodation.dto.model';
import { Accommodation } from './model/accommodation.model';
import { PriceListDTO } from './model/priceList.dto.model';
import { PriceList } from './model/priceList.model';
import { FilterDTO } from "./model/filter.dto.model";
import { SearchResponseDTO } from "./model/search-response.dto.model";
import {AccommodationDetailsDTO} from "./model/accommodation-details.dto.model";

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  private basicAccommodationList: AccommodationBasicModel[] = [];
  private accommodations: AccommodationDTO[] = [];

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getOwnerAccommodations(ownerId : number | undefined) : Observable<AccommodationBasicModel[]> {
    return this.httpClient.get<AccommodationBasicModel[]>(environment.apiAccommodation + '/'  + ownerId);
  }

  getForSearch(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number): Observable<SearchResponseDTO> {
    return this.httpClient.get<SearchResponseDTO>(environment.apiHost + 'accommodations/' +
      "search?location=" + location +
      "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
      "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
      "&persons=" + persons +
      "&page=" + page +
      "&size=" + size);
  }

  getAccommodationDetails(id: number): Observable<AccommodationDetailsDTO> {
    return this.httpClient.get<AccommodationDetailsDTO>(environment.apiHost + 'accommodations/details/' + id);
  }

  getForFilterAndSort(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number, sort: string, filter: FilterDTO): Observable<SearchResponseDTO> {
    return this.httpClient.post<SearchResponseDTO>(environment.apiHost + 'accommodations/' +
      "filter?location=" + location +
      "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
      "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
      "&persons=" + persons +
      "&page=" + page +
      "&size=" + size +
      "&sort=" + sort, filter);

  }

  getImage(imageId: number): Observable<Blob> {
    return this.httpClient.get(environment.apiHost + "accommodations/image/" + imageId, { responseType: 'blob' });
  }

  getAccommodationImages(accommodationId: number): Observable<string[]> {
    return this.httpClient.get<string[]>(environment.apiHost + "accommodations/images/" + accommodationId, {responseType:"json"});
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

  getAllPriceListItems(accommodationId: number): Observable<PriceList[]> {
    return this.httpClient.get<PriceList[]>(environment.apiAccommodation + '/' + accommodationId + "/getPrice");
  }

  add(ownerId: number, accommodation: AccommodationDTO): Observable<Accommodation> {
    const params = new HttpParams().set('ownerId', ownerId);
    return this.httpClient.post<Accommodation>(environment.apiAccommodation, accommodation, {params});
  }

  deletePriceListItem(accommodationId: number, priceListItem: PriceListDTO): Observable<PriceList> {
    return this.httpClient.delete<PriceList>(environment.apiAccommodation + "/price/" + accommodationId, {"body": priceListItem});
  }

  addImages(accommodationId: number, images: File[]) {
    const data: FormData = new FormData();
    images.forEach((file: File) => {
      data.append("images", file);
    })
    return this.httpClient.post<string[]>(environment.apiAccommodation + "/" + accommodationId, data);
  }

  addPriceList(accommodationId: number, priceList: PriceListDTO) {
    return this.httpClient.post<PriceListDTO>(environment.apiAccommodation + "/" + accommodationId + "/addPrice", priceList);
  }
}
