import {Injectable, Inject, LOCALE_ID} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Credentials} from "./model/credentials";
import {BehaviorSubject, Observable} from "rxjs";
import {UserJWT} from "./model/UserJWT";
import {environment} from "../../../env/env";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root',
})


export class AuthenticationService {
  constructor(@Inject(LOCALE_ID) private locale: string,
              private httpClient: HttpClient) {
  }

  user$ = new BehaviorSubject("");
  userState = this.user$.asObservable();

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    skip: 'true',
  });

  async getCountries(): Promise<string[]> {
    try {
      const response = await fetch('https://restcountries.com/v3.1/all');
      const countriesData = await response.json();
      const countries = countriesData.map((country: any) => country.name.common);
      countries.sort();
      return countries;
    } catch (error) {
      console.error('Error fetching countries:', error);
      return [];
    }
  }

  login(userCredentials: Credentials): Observable<UserJWT> {
    return this.httpClient.post<UserJWT>(environment.apiHost + "users/login", userCredentials,
      {headers: this.headers});
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('user') != null;
  }

  getRole(): any {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper: JwtHelperService = new JwtHelperService();
      return helper.decodeToken(accessToken).role;
    }
    return null;
  }

  setUser(): void {
    this.user$.next(this.getRole());
  }
}
