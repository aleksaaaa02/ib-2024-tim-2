import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AccommodationService} from "../accommodation.service";
import {AccommodationDTO} from "../model/accommodation.dto.model";
import {AccommodationDetailsDTO} from "../model/accommodation-details.dto.model";
import {AccountService} from "../../account/account.service";
import contains from "@popperjs/core/lib/dom-utils/contains";
import {FilterComponent} from "../../../layout/filter/filter.component";
import {MapComponent} from "../../../shared/map/map.component";
import {CarouselComponent} from "../carousel/carousel.component";

@Component({
  selector: 'app-accommodation-page',
  templateUrl: './accommodation-page.component.html',
  styleUrl: './accommodation-page.component.css'
})
export class AccommodationPageComponent implements OnInit{
  amenitiesMap: Map<string, string> = new Map([
    ['Free wifi', 'wifi'],
    ['Free parking', 'local_parking'],
    ['Terrace', 'balcony'],
    ['Breakfast', 'breakfast_dining'],
    ['Deposit box', 'local_atm'],
    ['Jacuzzi', 'hot_tub'],
    ['Wheelchair', 'accessible'],
    ['Non smoking', 'smoke_free'],
    ['Air conditioning', 'ac_unit'],
    ['Swimming pool', 'pool'],
    ['Bar', 'local_bar'],
    ['Sauna', 'sauna'],
    ['Luggage storage', 'luggage'],
    ['Lunch', 'lunch_dining'],
    ['Airport shuttle', 'airport_shuttle'],
    ['Family rooms', 'family_restroom'],
    ['Garden', 'yard'],
    ['Front desk', 'concierge'],
    ['Heating', 'heat'],
    ['Diner', 'dining'],
    ['Private bathroom', 'shower'],
    ['City center', 'location_city']
  ]);

  accommodation: AccommodationDetailsDTO;
  ownerImage: string | ArrayBuffer | null = null;
  accommodationImages: string[] = [];
  amenitiesList: [string, string][] = [];
  @ViewChild(MapComponent) mapComponent: MapComponent;
  @ViewChild(CarouselComponent) carouselComponent: CarouselComponent;

  constructor(private route: ActivatedRoute, private accommodationService: AccommodationService, private accountService: AccountService) {
  }

  ngOnInit(): void {
    window.scrollTo({
      top: 0,
      behavior: 'instant'
    });
    this.route.params.subscribe((params) => {
      const id = +params['accommodationId'];
      this.accommodationService.getAccommodationDetails(id).subscribe({
        next: (data) => {
          this.accommodation = data;
          this.changeDisplay();
          this.setAmenities();
          if (this.accommodation.owner.imageId != 0)
            this.getOwnerPhoto(this.accommodation.owner.imageId);
          else
            this.ownerImage = "../../assets/images/user.jpg";
          this.getAccommodationPhotos(id);
          const address = this.accommodation.address.address + ", " + this.accommodation.address.city + ", " + this.accommodation.address.zipCode + ", " + this.accommodation.address.country;
          this.mapComponent.search(address);
        }
      })
    });
  }

  getAccommodationPhotos(id: number){
    this.accommodationService.getAccommodationImages(id).subscribe( {
      next: (data): void => {
        for (const b of data){
          this.accommodationImages.push("data:image/*;base64,"+b);
        }
        this.carouselComponent.images = this.accommodationImages;
      }
    });
  }

  getOwnerPhoto(id: number){
    this.accountService.getAccountImage(this.accommodation.owner.imageId).subscribe({
      next: (data: Blob): void => {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.ownerImage = reader.result;
        }
        reader.readAsDataURL(data);
      },
      error: err => {
        console.error(err);
      }
    });
  }

  setAmenities(){
    if (this.accommodation.filters != null) {
      for (const filter of this.accommodation.filters) {
        let label = this.transformLabel(filter);
        this.amenitiesList.push([label, this.amenitiesMap.get(label) ?? '']);
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
    if (this.accommodation.owner?.avgRating == 0){
      const el1 = document.getElementById("ownerRating");
      if (el1 != null)
        el1.style.display = 'none';
    }
  }
}
