import { Component, Input } from '@angular/core';
import { CommentDTO } from '../model/comment.model.dto';
import { DatePipe } from '@angular/common';
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.css',
  providers: [DatePipe]
})
export class CommentComponent {
  @Input() comment: CommentDTO;
  guestId: number;

  constructor(public datepipe: DatePipe, private authenticationService: AuthenticationService) {
    this.guestId = authenticationService.getUserId();
  }

  formatDate(date: Date): string | null {
    return this.datepipe.transform(date, 'dd.MM.yyyy.');
  }

  deleteComment(id: number){
    //obrisi
  }
}
