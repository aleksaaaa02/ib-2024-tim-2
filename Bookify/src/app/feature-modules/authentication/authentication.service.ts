import { Injectable, Inject, LOCALE_ID } from "@angular/core";
@Injectable({
    providedIn: 'root',
})


export class AuthenticationService {
    constructor(@Inject(LOCALE_ID) private locale: string) {}

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
}