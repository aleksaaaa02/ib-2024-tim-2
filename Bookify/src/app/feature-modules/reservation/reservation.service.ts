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

  getAccommodationMapForGuest(userId: number): Observable<any[]> {
    return this.httpClient.get<any[]>(environment.apiHost + "reservations/accommodations/guest" + "?userId=" + userId);
  }

  getFilteredRequestsForGuest(userId: number, accommodationId: number, dateBegin: Date, dateEnd: Date, statuses: string[]): Observable<ReservationDTO[]> {
    return this.httpClient.get<ReservationDTO[]>(environment.apiHost + "reservations/guest/filter" +
                                                                            "?userId=" + userId +
                                                                            "&accommodationId=" + accommodationId +
                                                                            "&startDate=" + (moment(dateBegin)).format('DD.MM.YYYY') +
                                                                            "&endDate=" + (moment(dateEnd)).format('DD.MM.YYYY') +
                                                                            "&statuses=" + statuses);
  }

  getAllRequestsForOwner(userId: number): Observable<ReservationDTO[]> {
    return this.httpClient.get<ReservationDTO[]>(environment.apiHost + "reservations/owner" + "?userId=" + userId);
  }

  getAccommodationMapForOwner(userId: number): Observable<any[]> {
    return this.httpClient.get<any[]>(environment.apiHost + "reservations/accommodations/owner" + "?userId=" + userId);
  }

  deleteRequest(reservationId: number): Observable<string> {
    return this.httpClient.put(environment.apiHost + "reservations/delete/" + reservationId, {}, {responseType:"text"});
  }

  getFilteredRequestsForOwner(userId: number, accommodationId: number, dateBegin: Date, dateEnd: Date, statuses: string[]): Observable<ReservationDTO[]> {
    return this.httpClient.get<ReservationDTO[]>(environment.apiHost + "reservations/owner/filter" +
      "?userId=" + userId +
      "&accommodationId=" + accommodationId +
      "&startDate=" + (moment(dateBegin)).format('DD.MM.YYYY') +
      "&endDate=" + (moment(dateEnd)).format('DD.MM.YYYY') +
      "&statuses=" + statuses);
  }
}
