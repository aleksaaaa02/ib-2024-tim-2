import { Component } from '@angular/core';

@Component({
  selector: 'app-accommodation-page',
  templateUrl: './accommodation-page.component.html',
  styleUrl: './accommodation-page.component.css'
})
export class AccommodationPageComponent {
  name = 'Angular';
  imageObject = [{
    image: 'assets/images/image1.jpg',
    thumbImage: 'assets/images/image1.jpg',
  }, {
    image: 'assets/images/image2.jpg',
    thumbImage: 'assets/images/image2.jpg'
  }, {
    image: 'assets/images/image3.jpg',
    thumbImage: 'assets/images/image3.jpg',
  },{
    image: 'assets/images/image4.jpg',
    thumbImage: 'assets/images/image4.jpg',
  }, {
    image: 'assets/images/image5.jpg',
    thumbImage: 'assets/images/image5.jpg',
  }];
}
