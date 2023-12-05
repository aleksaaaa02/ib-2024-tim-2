import { Injectable } from '@angular/core';
import { AccommodationDTO } from './model/accommodation.dto.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../env/env';
import { Accommodation } from './model/accommodation.model';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  private accommodations: AccommodationDTO[] = [];

  constructor(private httpClient: HttpClient) { }

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

  // getAccommodation(id: number): Observable<Accommodation> {
  //   return this.httpClient.get<Accommodation>(environment.apiAccommodation + '/' + id)
  // }
}
