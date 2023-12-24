import {Inject, Injectable, LOCALE_ID} from "@angular/core";
import {FilterDTO} from "../accommodation/model/filter.dto.model";
import {Observable} from "rxjs";
import {SearchResponseDTO} from "../accommodation/model/search-response.dto.model";
import {environment} from "../../../env/env";
import moment from "moment/moment";
import {HttpClient} from "@angular/common/http";
import {ReservationRequestDTO} from "../accommodation/model/reservation-request.dto.model";
import {ReservationDTO} from "./model/ReservationDTO";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getAllRequestsForGuest(userId: number): Observable<ReservationDTO[]> {
    return this.httpClient.get<ReservationDTO[]>(environment.apiHost + "reservations/guest" + "?userId=" + userId);
  }

  // getForFilterAndSort(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number, sort: string, filter: FilterDTO): Observable<SearchResponseDTO> {
  //   return this.httpClient.post<SearchResponseDTO>(environment.apiHost + 'accommodations/' +
  //     "filter?location=" + location +
  //     "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
  //     "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
  //     "&persons=" + persons +
  //     "&page=" + page +
  //     "&size=" + size +
  //     "&sort=" + sort, filter);
  //
  // }
}
