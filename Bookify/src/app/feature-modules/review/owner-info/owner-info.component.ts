import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { OwnerDTO } from '../model/owner.model.dto';

@Component({
  selector: 'app-owner-info',
  templateUrl: './owner-info.component.html',
  styleUrl: './owner-info.component.css'
})
export class OwnerInfoComponent implements OnInit {
  ownerId: number;
  ownerFirstName: string;
  ownerLastName: string;
  ownerPhone: string;

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['ownerId'];
    });
    if(!Number.isNaN(this.ownerId)){
      this.reviewServise.getOwnerDTO(this.ownerId).subscribe({
        next: (owner: OwnerDTO) => {
          this.ownerFirstName = owner.firstName;
          this.ownerLastName = owner.lastName;
          this.ownerPhone = owner.phone;
        }
      });
    }
  }

}
