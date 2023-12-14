import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements AfterViewInit{
  uuid: string;
  message: string;

  constructor(private authenticationService: AuthenticationService, private route: ActivatedRoute) {}
  
  ngAfterViewInit(): void {
    this.route.queryParams.subscribe(params => {
      this.uuid = params['uuid'] || "";
    });
    console.log(this.uuid);
    // this.authenticationService.activateAccount(this.uuid).subscribe({
    //   next: (message) => {
    //     this.message = message.token;
    //   },
    //   error: (message) => {
    //     this.message = message.token;
    //   }
    // });
  }
}
