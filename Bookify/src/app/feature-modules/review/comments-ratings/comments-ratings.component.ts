import { Component } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { CommentDTO } from '../model/comment.model.dto';

@Component({
  selector: 'app-comments-ratings',
  templateUrl: './comments-ratings.component.html',
  styleUrl: './comments-ratings.component.css'
})
export class CommentsRatingsComponent {
  ownerId: number;
  comments: CommentDTO[];

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['ownerId'];
    });
    if (!Number.isNaN(this.ownerId)) {
      this.reviewServise.getOwnerComments(this.ownerId).subscribe({
        next: (comments: CommentDTO[]) => {
          this.comments = comments;
        }
      })
    }
  }
}
