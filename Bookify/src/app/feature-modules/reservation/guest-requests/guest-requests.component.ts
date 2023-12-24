import {Component, OnInit} from '@angular/core';
import {ReservationService} from "../reservation.service";
import {AuthenticationService} from "../../authentication/authentication.service";
import {ReservationRequestDTO} from "../../accommodation/model/reservation-request.dto.model";
import {ReservationDTO} from "../model/ReservationDTO";

@Component({
  selector: 'app-guest-requests',
  templateUrl: './guest-requests.component.html',
  styleUrl: './guest-requests.component.css'
})
export class GuestRequestsComponent implements OnInit {
  requests: ReservationDTO[];
  constructor(private reservationService: ReservationService, private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.getResults();
  }

  getResults() {
    this.reservationService.getAllRequestsForGuest(this.authenticationService.getUserId()).subscribe({
      next: (data) => {
        this.requests = data;
      }
    })
  }

}
