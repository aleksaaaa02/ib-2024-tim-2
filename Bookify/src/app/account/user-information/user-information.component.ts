import { Component } from '@angular/core';
import { AuthenticationService } from "../../feature-modules/authentication/authentication.service";

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrl: './user-information.component.css',

})
export class UserInformationComponent {
  countries: Promise<string[]> = Promise.resolve([]);

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.countries = this.authenticationService.getCountries();
  }

}
