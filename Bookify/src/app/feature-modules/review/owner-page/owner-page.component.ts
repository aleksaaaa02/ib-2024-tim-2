import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { OwnerDTO } from '../model/owner.model.dto';

@Component({
  selector: 'app-owner-page',
  templateUrl: './owner-page.component.html',
  styleUrl: './owner-page.component.css'
})
export class OwnerPageComponent {

  // ownerId: number;

  // constructor(private reviewServise: ReviewService, private route: ActivatedRoute) { }

  // ngOnInit(): void {
  //   this.route.params.subscribe(params => {
  //     this.ownerId = +params['accommodationId'];
  //   });
  //   this.reviewServise.getOwnerDTO(this.ownerId).subscribe({
  //     next: (owner: OwnerDTO) => {
        
  //     }
  //   })
  // }

}
