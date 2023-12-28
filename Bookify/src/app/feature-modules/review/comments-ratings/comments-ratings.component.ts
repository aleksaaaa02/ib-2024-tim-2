import { ChangeDetectorRef, Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { CommentDTO } from '../model/comment.model.dto';

@Component({
  selector: 'app-comments-ratings',
  templateUrl: './comments-ratings.component.html',
  styleUrl: './comments-ratings.component.css'
})
export class CommentsRatingsComponent implements OnChanges {
  @Input() load : boolean;
  ownerId: number;
  comments: CommentDTO[];
  loading: boolean = false;

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute, private cdr: ChangeDetectorRef) { }
 
  ngOnChanges(changes: SimpleChanges): void {
    if(this.load){
      this.reviewServise.getOwnerComments(this.ownerId).subscribe({
        next: (comments: CommentDTO[]) => {
          this.comments = comments;
        }
      });
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['ownerId'];
    });
    if (!Number.isNaN(this.ownerId)) {
      this.reviewServise.getOwnerComments(this.ownerId).subscribe({
        next: (comments: CommentDTO[]) => {
          this.comments = comments;
        }
      });
    }
  }

  onEmitChange(data: boolean): void {
    console.log("JAOO");
    this.reviewServise.getOwnerComments(this.ownerId).subscribe({
      next: (comments: CommentDTO[]) => {
        this.comments = comments;
        this.loading = true;
        this.load = false;
        this.cdr.detectChanges();
      }
    });
  }

  onLoadingChange(data: boolean): void {
    this.loading = false; 
  }
}
