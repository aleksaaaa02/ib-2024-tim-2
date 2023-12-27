import {Component, OnInit} from '@angular/core';
import {AdministrationService} from "../administration.service";
import {User} from "../model/user";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit{

  allUsers: User[] = []

  constructor(private adminService: AdministrationService) {
  }
  ngOnInit(): void {
    this.adminService.getAllUsers().subscribe({
      next: (users: User[]) => {
        this.allUsers = users;
      },
      error: err => {}

    });

  }

}
