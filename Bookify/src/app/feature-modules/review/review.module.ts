import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewCommentComponent } from './new-comment/new-comment.component';
import { MaterialModule } from "../../infrastructure/material/material.module";
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommentComponent } from './comment/comment.component';
import { RatingsComponent } from './ratings/ratings.component';
import { CommentsRatingsComponent } from './comments-ratings/comments-ratings.component';
import { OwnerPageComponent } from './owner-page/owner-page.component';
import { OwnerInfoComponent } from './owner-info/owner-info.component';
import { ReportComponent } from './report/report.component';

@NgModule({
  declarations: [
    NewCommentComponent,
    CommentComponent,
    RatingsComponent,
    CommentsRatingsComponent,
    OwnerPageComponent,
    OwnerInfoComponent,
    ReportComponent
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
    CommentsRatingsComponent,
    OwnerPageComponent
  ]
})
export class ReviewModule { }
