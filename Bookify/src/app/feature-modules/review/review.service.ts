import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from './model/user.model.dto';
import { environment } from '../../../env/env';
import { RatingDTO } from './model/rating.model.dto';
import { CommentDTO } from './model/comment.model.dto';
import { NewCommentDTO } from './model/new-comment.model.dto';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getUserDTO(userId: number): Observable<UserDTO>{
    return this.httpClient.get<UserDTO>(environment.apiUser + "/user/" + userId);
  }

  getOwnerRating(ownerId: number): Observable<RatingDTO>{
    return this.httpClient.get<RatingDTO>(environment.apiReview + "/owner/" + ownerId + "/rating");
  }

  getOwnerComments(ownerId: number): Observable<CommentDTO[]>{
    return this.httpClient.get<CommentDTO[]>(environment.apiReview + "/owner/" + ownerId);
  }

  add(ownerId: number,newComment: NewCommentDTO) {
    return this.httpClient.post<null>(environment.apiReview + "/new-owner/" + ownerId, newComment);
  }

  delete(ownerId: number, reviewId: number) {
    return this.httpClient.delete<null>(environment.apiReview + "/owner-delete/" + ownerId + "/" + reviewId);
  }
  
  reportReview(reviewId: number): Observable<number> {
    return this.httpClient.put<number>(environment.apiReview + "/report/" + reviewId, null);
  }
}
