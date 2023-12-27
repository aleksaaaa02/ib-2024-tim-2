import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withNoHttpTransferCache } from '@angular/platform-browser';
import { AccommodationModule } from './feature-modules/accommodation/accommodation.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from "./layout/layout.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthenticationModule } from './feature-modules/authentication/authentication.module';
import { CarouselComponent } from "./feature-modules/accommodation/carousel/carousel.component";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { DatapickerRangeComponent } from "./layout/datapicker-range/datapicker-range.component";
import { ReservationModule } from './feature-modules/reservation/reservation.module';
import {Interceptor} from "./feature-modules/authentication/interceptor/interceptor";
import {AdministrationModule} from "./feature-modules/administration/administration.module";
import {AccountModule} from "./feature-modules/account/account.module";
import {SharedModule} from "./shared/shared.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    AccountModule,
    BrowserAnimationsModule,
    AuthenticationModule,
    CarouselComponent,
    NgbModule,
    HttpClientModule,
    DatapickerRangeComponent,
    AccommodationModule,
    ReservationModule,
    SharedModule,
    SharedModule,
    AdministrationModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true},
    provideClientHydration(withNoHttpTransferCache()),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
