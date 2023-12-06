import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { AccommodationDTO } from './model/accommodation.dto.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../env/env';
import { Accommodation } from './model/accommodation.model';
import { PriceList } from './model/priceList.dto';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  private accommodations: AccommodationDTO[] = [];

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

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

  // getAll(): Observable<Accommodation[]> {
  //   return this.httpClient.get<Accommodation[]>(environment.apiAccommodation + '')
  // }

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

  addPriceList(accommodationId: number, priceList: PriceList) {
    return this.httpClient.post<PriceList>(environment.apiAccommodation + "/price/" + accommodationId, priceList);
  }

  // getAccommodation(id: number): Observable<Accommodation> {
  //   return this.httpClient.get<Accommodation>(environment.apiAccommodation + '/' + id)
  // }
}
