import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "./model/account";
import {environment} from "../../../env/env";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient: HttpClient) {

  }

  getUser(userId: string): Observable<Account> {
    return this.httpClient.get<Account>(environment.apiHost + "users/" + userId);
  }

  getAccountImage(imageId: number | undefined): Observable<Blob> {
    return this.httpClient.get(environment.apiHost + "users/image/" + imageId, {responseType: "blob"});
  }

  updatePassword(userId: string | undefined, newPassword: string): Observable<string>{
      return this.httpClient.post(environment.apiHost + "users/" + userId + "/change-password", newPassword, {responseType: "text"});
  }

  updateUser(account: Account): Observable<Account> {
    return this.httpClient.put<Account>(environment.apiHost + "users", account);
  }

  updateAccountImage(userId: string | undefined, file: File): Observable<number> {
    const data: FormData = new FormData();
    data.append('image', file);
    return this.httpClient.post<number>(environment.apiHost + "users/change-image/" + userId, data);
  }
  deleteAccount(userId: string | undefined): Observable<string>{
    return this.httpClient.delete(environment.apiHost + "users/" + userId, {responseType: "text"});
  }
  getAccountImageId(userId: string | undefined): Observable<number>{
    return this.httpClient.get<number>(environment.apiHost + "users/account-pic/" + userId);
  }
}
