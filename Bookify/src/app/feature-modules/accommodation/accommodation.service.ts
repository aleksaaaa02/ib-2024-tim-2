import { Injectable } from '@angular/core';
import {AccommodationBasicModel} from "./model/accommodation-basic.model";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {environment} from "../../../env/env";
import moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  private basicAccommodationList: AccommodationBasicModel[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getForSearch(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number): Observable<AccommodationBasicModel[]> {
    return this.httpClient.get<AccommodationBasicModel[]>(environment.apiHost + 'accommodations/' +
                                                                                    "search?location=" + location +
                                                                                    "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
                                                                                    "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
                                                                                    "&persons=" + persons +
                                                                                    "&page=" + page +
                                                                                    "&size=" + size);
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
}
