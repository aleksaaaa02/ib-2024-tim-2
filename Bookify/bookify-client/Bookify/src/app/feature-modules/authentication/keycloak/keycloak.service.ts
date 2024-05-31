import { Injectable } from '@angular/core';
import Keycloak from "keycloak-js";
import {UserProfile} from "./UserProfile";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  get profile(): UserProfile | undefined {
    return this._profile;
  }

  get keycloak(){
    if(!this._keycloak){
      this._keycloak = new Keycloak({
        url: 'http://127.0.0.1:8080',
        realm: 'Bookify',
        clientId: 'bookify'
      });

    }
    return this._keycloak;
  }

  constructor() { }

  async init(){
    const authenticated = await this.keycloak?.init({
      onLoad: 'check-sso',
    });
    if(authenticated) {
      this._profile = (await  this.keycloak?.loadUserProfile()) as UserProfile;
      this._profile.token = this.keycloak?.token; // Getting the token or tokenParsed for specific claim
      this._profile.roles = this.keycloak?.tokenParsed?.realm_access?.roles;
      console.log(this.keycloak?.tokenParsed);
    }
  }

  login() {
    return this.keycloak?.login();
  }

  logout() {
    this._profile = undefined;
    return this.keycloak?.logout({redirectUri: 'https://localhost:4200/'});
  }
}
