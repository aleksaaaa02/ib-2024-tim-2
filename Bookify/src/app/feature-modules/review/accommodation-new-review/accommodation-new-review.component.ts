import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NewCommentDTO } from '../model/new-comment.model.dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ReviewService } from '../review.service';
import { AuthenticationService } from '../../authentication/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-accommodation-new-review',
  templateUrl: './accommodation-new-review.component.html',
  styleUrl: './accommodation-new-review.component.css'
})
export class AccommodationNewReviewComponent implements OnInit {
  @Output() emit = new EventEmitter<boolean>();
  
  percent: number = 0;
  form: FormGroup;
  submitted: boolean;

  accommodationId: number;
  
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private reviewService: ReviewService,
     private authenticationService: AuthenticationService, private _snackBar: MatSnackBar) {
    this.form = this.fb.group({
      comment: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.accommodationId = +params['accommodationId'];
    });
  }

  onRatingClick(event: MouseEvent) {
    const meter = event.target as HTMLMeterElement;
    this.percent = event.offsetX / meter.offsetWidth;
    this.percent *= 5;
    this.percent = Math.ceil(this.percent);
  }

  onSubmit() {

  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

}
