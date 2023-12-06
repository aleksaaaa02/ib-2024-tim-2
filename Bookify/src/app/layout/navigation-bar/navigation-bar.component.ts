import {ChangeDetectionStrategy, Component, HostListener} from '@angular/core';
import {NavigationEnd, NavigationStart, Router} from "@angular/router";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css',
})
export class NavigationBarComponent {
    isScrolled: boolean = false;
    currentRoute: string = "";
    isTransparentRoute: boolean = false;
    allowedScrollRoutes: string[] = ['', ' ', '/', '/results'];

    constructor(private router: Router) {
        router.events.subscribe((event) =>{
          if(event instanceof NavigationEnd) {
            this.currentRoute = event.url;
            this.checkIfInitialTransparent();
          }
        });
    }

    @HostListener('window:scroll', [])
    onWindowScroll():void{
      if(this.isTransparentRoute) {
        this.isScrolled = window.scrollY > 350;
      }
    }

    private checkIfInitialTransparent() : void{
      if (this.currentRoute.includes('/results'))
        this.isTransparentRoute = true;
      else
        this.isTransparentRoute = this.allowedScrollRoutes.includes(this.currentRoute);
      if(this.isTransparentRoute){
        this.isScrolled = false;
        return;
      }
      this.isScrolled = true;
    }

}
