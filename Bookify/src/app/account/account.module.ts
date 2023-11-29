import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { UserInformationComponent } from './user-information/user-information.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { NotificationSettingsComponent } from './notification-settings/notification-settings.component';
import {MaterialModule} from "../infrastructure/material/material.module";
import {AccountComponent} from "./account/account.component";



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
    NgOptimizedImage
  ],

  exports: [
    AccountComponent

  ]
})
export class AccountModule { }
