import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccommodationBasicComponent } from './feature-modules/accommodation-basic/accommodation-basic.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import { FilterComponent } from './feature-modules/filter/filter.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSliderModule} from "@angular/material/slider";
import {MatInputModule} from "@angular/material/input";
import { SearchComponent } from './feature-modules/search/search.component';
import { ResultsPageComponent } from './feature-modules/results-page/results-page.component';
import {MatSelectModule} from "@angular/material/select";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatDatepickerModule} from "@angular/material/datepicker";
import { AccommodationPageComponent } from './feature-modules/accommodation-page/accommodation-page.component';
import {NgImageSliderModule} from "ng-image-slider";
import { ReserveComponent } from './feature-modules/reserve/reserve.component';

@NgModule({
  declarations: [
    AppComponent,
    AccommodationBasicComponent,
    FilterComponent,
    SearchComponent,
    ResultsPageComponent,
    AccommodationPageComponent,
    ReserveComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonToggleModule,
    MatCardModule,
    MatButtonModule,
    MatCheckboxModule,
    MatSliderModule,
    MatInputModule,
    MatSelectModule,
    MatPaginatorModule,
    MatDatepickerModule,
    NgImageSliderModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
