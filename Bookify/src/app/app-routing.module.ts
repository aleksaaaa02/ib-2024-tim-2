import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ResultsPageComponent} from "./feature-modules/results-page/results-page.component";
import {AccommodationPageComponent} from "./feature-modules/accommodation-page/accommodation-page.component";
import {LandingPageComponent} from "./feature-modules/landing-page/landing-page.component";

const routes: Routes = [
  { path: "results", component: ResultsPageComponent },
  { path: "accommodation", component: AccommodationPageComponent},
  {path: '', component: LandingPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
