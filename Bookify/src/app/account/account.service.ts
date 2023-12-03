import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "./model/account";
import {environment} from "../../env/env";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient: HttpClient) {
  }

  getUser(userId: number): Observable<Account> {
    return this.httpClient.get<Account>(environment.apiHost + "users/" +userId);
  }
}
