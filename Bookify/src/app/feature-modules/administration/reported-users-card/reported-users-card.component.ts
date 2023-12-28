import {Component, Input, OnInit} from '@angular/core';
import {ReportedUser} from "../model/reported.user";

@Component({
  selector: 'app-reported-users-card',
  templateUrl: './reported-users-card.component.html',
  styleUrl: './reported-users-card.component.css'
})
export class ReportedUsersCardComponent implements OnInit{
  @Input()
  report: ReportedUser

  constructor() {
  }

  ngOnInit(): void {
    console.log(this.report);
  }
}
