import {Component, Input, OnInit} from '@angular/core';
import {ReservationService} from "../reservation.service";
import {AccommodationService} from "../../accommodation/accommodation.service";
import {ReservationGuestViewDTO} from "../model/ReservationGuestViewDTO";

@Component({
  selector: 'app-guest-reservation-card',
  templateUrl: './guest-reservation-card.component.html',
  styleUrl: './guest-reservation-card.component.css'
})
export class GuestReservationCardComponent implements OnInit {
  @Input() reservation: ReservationGuestViewDTO;
  image: string | ArrayBuffer | null;

  constructor(private reservationService: ReservationService, private accommodationService: AccommodationService) {
  }

  ngOnInit(): void {
    this.accommodationService.getImage(this.reservation.imageId).subscribe({
      next: (data) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.image = reader.result;
        }
        reader.readAsDataURL(data);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
}
