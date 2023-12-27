import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { OwnerDTO } from '../model/owner.model.dto';
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-owner-page',
  templateUrl: './owner-page.component.html',
  styleUrl: './owner-page.component.css'
})
export class OwnerPageComponent {
  load: boolean = false;
  role: string;

  constructor(private authenticationService: AuthenticationService) {
    this.role = authenticationService.getRole();
   }

  handleEmit(data: boolean) {
    this.load = data;
  }

}
