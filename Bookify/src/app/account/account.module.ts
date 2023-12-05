import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { UserInformationComponent } from './user-information/user-information.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { NotificationSettingsComponent } from './notification-settings/notification-settings.component';
import { MaterialModule } from "../infrastructure/material/material.module";
import { AccountComponent } from "./account/account.component";
import { MatOptionModule } from "@angular/material/core";
import { MatSelectModule } from "@angular/material/select";
import {HttpClientModule} from "@angular/common/http";
import { AuthenticationService } from "../feature-modules/authentication/authentication.service";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    AccountComponent,
    UserInformationComponent,
    NotificationsComponent,
    NotificationSettingsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    NgOptimizedImage,
    MatOptionModule,
    MatSelectModule,
    HttpClientModule,
    ReactiveFormsModule
  ],

  exports: [
    AccountComponent

  ]
})
export class AccountModule { }
