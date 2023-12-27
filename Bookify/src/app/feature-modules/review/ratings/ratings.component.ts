import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { RatingDTO } from '../model/rating.model.dto';

@Component({
  selector: 'app-ratings',
  templateUrl: './ratings.component.html',
  styleUrl: './ratings.component.css'
})
export class RatingsComponent implements OnInit, OnChanges {
  @Input() load: boolean;
  @Output() loadingChange = new EventEmitter<boolean>();
  ownerId: number;

  oneStar: number;
  twoStars: number;
  threeStars: number;
  fourStars: number;
  fiveStars: number;
  sum: number;

  sumProgress: number;

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute) { }

  ngOnChanges(changes: SimpleChanges): void {
    console.log("Ocenice");
    console.log(this.load);
    if (this.load) {
      this.getRating();
      this.loadingChange.emit(true);
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['ownerId'];
    });
    if (!Number.isNaN(this.ownerId)) {
      this.getRating();
    }
  }

  getRating(){
    this.reviewServise.getOwnerRating(this.ownerId).subscribe({
      next: (rating: RatingDTO) => {
        this.oneStar = rating.oneStars;
        this.twoStars = rating.twoStars;
        this.threeStars = rating.threeStars;
        this.fourStars = rating.fourStars;
        this.fiveStars = rating.fiveStars;
        this.sum = this.oneStar + this.twoStars + this.threeStars + this.fourStars + this.fiveStars;

        const temp = this.oneStar + 2 * this.twoStars + 3 * this.threeStars + 4 * this.fourStars + 5 * this.fiveStars;
        this.sumProgress = temp / this.sum;
      }
    });
  }
}
