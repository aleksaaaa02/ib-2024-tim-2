import { Component } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-guest-page',
  templateUrl: './guest-page.component.html',
  styleUrl: './guest-page.component.css'
})
export class GuestPageComponent {
  role: string;

  constructor(private authenticationService: AuthenticationService) {
    this.role = authenticationService.getRole();
   }
}
