import {Component, OnInit} from '@angular/core';
import {Account} from "../model/account";
import {AccountService} from "../account.service";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit {

  account: Account = {}

  constructor(private accountService: AccountService) {

  }

  ngOnInit(): void {
    this.accountService.getUser(1231).subscribe({
      next: (data: Account) => {
        this.account = data;
      },
      error: (_) => {console.log("Error occurred!")}
    })
  }

}
