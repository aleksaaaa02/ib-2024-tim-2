import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ReviewService } from '../review.service';
import { NewCommentDTO } from '../model/new-comment.model.dto';
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrl: './new-comment.component.css'
})
export class NewCommentComponent implements OnInit {
  percent: number = 0;
  form: FormGroup;
  submitted: boolean;

  ownerId: number;
  
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private reviewService: ReviewService, private authenticationService: AuthenticationService) {
    this.form = this.fb.group({
      comment: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['ownerId'];
    });
  }

  onRatingClick(event: MouseEvent) {
    const meter = event.target as HTMLMeterElement;
    this.percent = event.offsetX / meter.offsetWidth;
    this.percent *= 5;
    this.percent = Math.ceil(this.percent);
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.valid && this.percent > 0) {
      const comment: NewCommentDTO = {
        comment: this.form.get('comment')?.value,
        rate: this.percent,
        guestId: this.authenticationService.getUserId()
      } 
      this.reviewService.add(this.ownerId, comment).subscribe({
        
      })
    }
  }
}
