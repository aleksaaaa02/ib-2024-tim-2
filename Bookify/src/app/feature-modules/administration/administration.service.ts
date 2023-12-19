import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccommodationRequests} from "./model/accommodation.requests";
import {environment} from "../../../env/env";

@Injectable({
    providedIn: 'root'
})
export class AdministrationService {

    constructor(private httpClient: HttpClient) {
    }


    getAccommodationRequests(): Observable<AccommodationRequests[]> {
        return this.httpClient.get<AccommodationRequests[]>(environment.apiAccommodation + "/requests")
    }

    rejectAccommodationRequest(accommodationId: number): Observable<string> {
        return this.httpClient.put(environment.apiAccommodation + "/reject/" + accommodationId, {}, {responseType:"text"});
    }

    approveAccommodationRequest(accommodationId: number): Observable<string> {
        return this.httpClient.put(environment.apiAccommodation + "/approve/" + accommodationId,{}, {responseType:"text"});
    }
}
