import { Injectable } from '@angular/core';
import { Accommodation } from './model/accommodation.dto.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../env/env';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  private accommodations: Accommodation[] = [];

  constructor(private httpClient: HttpClient) {}

  // getAll(): Observable<Accommodation[]> {
  //   return this.httpClient.get<Accommodation[]>(environment.apiAccommodation + '')
  // }

  add(accommodation: Accommodation): Observable<Accommodation> {
    return this.httpClient.post<Accommodation>(environment.apiAccommodation, accommodation)
  }

  // getAccommodation(id: number): Observable<Accommodation> {
  //   return this.httpClient.get<Accommodation>(environment.apiAccommodation + '/' + id)
  // }
}
