import {Component, Input} from '@angular/core';
import { AuthenticationService } from "../../feature-modules/authentication/authentication.service";
import {AccountService} from "../account.service";
import {Account} from "../model/account";

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrl: './user-information.component.css',

})
export class UserInformationComponent {
  countries: Promise<string[]> = Promise.resolve([]);

  @Input()
  account: Account

  constructor(private authenticationService: AuthenticationService,
              private accountService: AccountService) {

  }

  ngOnInit(): void {
    this.countries = this.authenticationService.getCountries();
  }

}
