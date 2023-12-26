import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewCommentComponent } from './new-comment/new-comment.component';
import { MaterialModule } from "../../infrastructure/material/material.module";
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommentComponent } from './comment/comment.component';
import { RatingsComponent } from './ratings/ratings.component';
import { CommentsRatingsComponent } from './comments-ratings/comments-ratings.component';

@NgModule({
  declarations: [
    NewCommentComponent,
    CommentComponent,
    RatingsComponent,
    CommentsRatingsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports:[
    NewCommentComponent,
    CommentComponent,
    RatingsComponent,
    CommentsRatingsComponent
  ]
})
export class ReviewModule { }
