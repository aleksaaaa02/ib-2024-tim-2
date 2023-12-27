import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommentDTO } from '../model/comment.model.dto';
import { DatePipe } from '@angular/common';
import { AuthenticationService } from '../../authentication/authentication.service';
import { ActivatedRoute } from '@angular/router';
import { ReviewService } from '../review.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.css',
  providers: [DatePipe]
})
export class CommentComponent implements OnInit {
  @Input() comment: CommentDTO;
  @Output() emit = new EventEmitter<boolean>();
  guestId: number;
  ownerId: number;

  constructor(public datepipe: DatePipe, private authenticationService: AuthenticationService, private route: ActivatedRoute, private reviewService: ReviewService) {
    this.guestId = authenticationService.getUserId();
  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['ownerId'];
    });
  }

  formatDate(date: Date): string | null {
    return this.datepipe.transform(date, 'dd.MM.yyyy.');
  }

  deleteComment(id: number) {
    this.reviewService.delete(this.ownerId, id).subscribe({
      next: (_) => {
        this.emit.emit(true);
      }
    });
  }
}
