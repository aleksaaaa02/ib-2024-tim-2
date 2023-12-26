import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewCommentComponent } from './new-comment/new-comment.component';
import { MaterialModule } from "../../infrastructure/material/material.module";
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    NewCommentComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[
    NewCommentComponent
  ]
})
export class ReviewModule { }
