import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "./model/account";
import {environment} from "../../env/env";
import {Resource} from "@angular/compiler-cli/src/ngtsc/metadata";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient: HttpClient) {

  }

  getUser(userId: number): Observable<Account> {
    return this.httpClient.get<Account>(environment.apiHost + "users/" +userId);
  }

  async getAccountImage(imageId: number | undefined): Promise<Blob> {
    try {
      const response = await fetch(environment.apiHost + "users/image/"+3);
      return  await response.blob();

    } catch (error) {
      console.error('Error fetching countries:', error);
      return new Blob();
    }
  }
  updateUser(userId: number | undefined, account: Account):Observable<Account> {
    return this.httpClient.put<Account>(environment.apiHost + "users", account);
  }
}
