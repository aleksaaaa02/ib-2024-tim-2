import {CanActivateFn, Router} from '@angular/router';
import {AuthenticationService} from "../authentication.service";
import {inject} from "@angular/core";
import {AdminPaths} from "./adminpaths";
import {GuestPaths} from "./guestpaths";
import {OwnerPaths} from "./ownerpaths";

export const authGuard: CanActivateFn = (route, state) => {
  const authenticationService: AuthenticationService = inject(AuthenticationService);
  const router: Router = inject(Router);
  const path: string = route.url[0].path;
  const userRole = authenticationService.getRole();
  console.log(userRole);
  console.log(path);
  if(userRole === 'ADMIN' && AdminPaths.includes(path)){
    return true;
  } else if(userRole === 'GUEST' && GuestPaths.includes(path)) {
    return true;
  } else if(userRole === 'OWNER' && OwnerPaths.includes(path)){
    return true;
  } else {
    router.navigate(['']);
    return false;
  }
};
