import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AccommodationService} from "../accommodation.service";
import {AccommodationDTO} from "../model/accommodation.dto.model";
import {AccommodationDetailsDTO} from "../model/accommodation-details.dto.model";
import {AccountService} from "../../account/account.service";
import contains from "@popperjs/core/lib/dom-utils/contains";

@Component({
  selector: 'app-accommodation-page',
  templateUrl: './accommodation-page.component.html',
  styleUrl: './accommodation-page.component.css'
})
export class AccommodationPageComponent implements OnInit{
  // name = 'Angular';
  // imageObject = [{
  //   image: 'assets/images/image1.jpg',
  //   thumbImage: 'assets/images/image1.jpg',
  // }, {
  //   image: 'assets/images/image2.jpg',
  //   thumbImage: 'assets/images/image2.jpg'
  // }, {
  //   image: 'assets/images/image3.jpg',
  //   thumbImage: 'assets/images/image3.jpg',
  // },{
  //   image: 'assets/images/image4.jpg',
  //   thumbImage: 'assets/images/image4.jpg',
  // }, {
  //   image: 'assets/images/image5.jpg',
  //   thumbImage: 'assets/images/image5.jpg',
  // }];

  amenitiesMap: Map<string, string> = new Map([
    ['Free wifi', 'wifi'],
    ['Free parking', 'local_parking'],
    ['Terrace', 'balcony'],
    ['Breakfast', 'breakfast_dining'],
    ['Deposit box', 'local_atm'],
    ['Jacuzzi', 'hot_tub'],
    ['Wheelchair', 'accessible'],
    ['Non-smoking', 'smoke_free'],
    ['Air conditioning', ''],
    ['Swimming pool', ''],
    ['Bar', ''],
    ['Sauna', ''],
    ['Luggage storage', ''],
    ['Lunch', ''],
    ['Airport shuttle', ''],
    ['Family rooms', ''],
    ['Garden', ''],
    ['Front desk', ''],
    ['Heating', ''],
    ['Diner', ''],
    ['Private bathroom', ''],
    ['City center', '']
  ]);

  accommodation: AccommodationDetailsDTO;
  ownerImage: string | ArrayBuffer | null = null;
  accommodationImages: string[] | ArrayBuffer[] | null = null;

  constructor(private route: ActivatedRoute, private accommodationService: AccommodationService, private accountService: AccountService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const id = +params['accommodationId'];
      this.accommodationService.getAccommodationDetails(id).subscribe({
        next: (data) => {
          this.accommodation = data;
          this.changeDisplay();
          this.setAmenities();
        }
      })
    });
    // this.accountService.getAccountImage(this.accommodation.owner.imageId).subscribe({
    //   next: (data: Blob): void => {
    //     const reader = new FileReader();
    //     reader.onloadend = () => {
    //       this.ownerImage = reader.result;
    //       console.log(this.ownerImage);
    //     }
    //     reader.readAsDataURL(data);
    //   },
    //   error: err => {
    //     console.error(err);
    //   }
    // });
    // this.accommodationService.getAccommodationImages(this.accommodation.id).subscribe( {
    //   next: (data): void => {
    //     const reader= new FileReader();
    //     reader.onloadend = () => {
    //       console.log(reader.result);
    //       // this.accommodationImages = reader.result;
    //     }
    //     // reader.readAsDataURL(data);
    //   }
    // });

  }

  setAmenities(){
    if (this.accommodation.filters != null) {
      for (const filter of this.accommodation.filters) {
        console.log(this.transformLabel(filter));
      }
    }
  }

  transformLabel(label: string): string {
    label = label.replace("_", " ");
    return label.charAt(0) + label.slice(1).toLowerCase();
  }

  changeDisplay(){
    if (this.accommodation.avgRating == 0) {
      const el = document.getElementById("accRating");
      if (el != null)
        el.style.display = 'none';
    }
  }
}
