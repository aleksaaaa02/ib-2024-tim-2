import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {AccountService} from "../account.service";
import {Account} from "../model/account";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialog} from "@angular/material/dialog";
import {PasswordChangeDialogComponent} from "../password-change-dialog/password-change-dialog.component";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {DefaultSnackbarComponent} from "../../../layout/default-snackbar/default-snackbar.component";
import { AccountDeleteDialogComponent } from '../account-delete-dialog/account-delete-dialog.component';

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrl: './user-information.component.css',

})
export class UserInformationComponent implements OnInit {
  account: Account = {};
  countries: Promise<string[]> = Promise.resolve([]);
  isDisabled: boolean = true;
  image: string | ArrayBuffer | null = null;

  userInfoForm: FormGroup = new FormGroup({
    firstname: new FormControl({
      value: '',
      disabled: this.isDisabled
    }, [Validators.required]),
    lastname: new FormControl({
      value: '',
      disabled: this.isDisabled
    }, [Validators.required]),
    city: new FormControl({
      value: '',
      disabled: this.isDisabled
    }, [Validators.required]),
    address: new FormControl({
      value: '',
      disabled: this.isDisabled
    }, [Validators.required]),
    zipcode: new FormControl({
      value: '',
      disabled: this.isDisabled
    }, [Validators.required]),
    phone: new FormControl({
      value: '',
      disabled: this.isDisabled
    }, [Validators.required]),
    country: new FormControl({
      value: '',
      disabled: this.isDisabled
    }, [Validators.required, Validators.nullValidator])
  });

  constructor(private authenticationService: AuthenticationService,
              private accountService: AccountService,
              public dialog: MatDialog,
              private router: Router,
              private snackbar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.accountService.getUser(this.authenticationService.getUserId()).subscribe({
      next: (data: Account) => {
        this.account = data;
        this.setFormData();
        this.userInfoForm.updateValueAndValidity();
        this.accountService.getAccountImage(this.account.imageId).subscribe({
          next: (data: Blob): void => {
            const reader = new FileReader();
            reader.onloadend = () => {
              this.image = reader.result;

            }
            reader.readAsDataURL(data);
          },
          error: err => {
            console.error(err);
          }
        })
      },
      error: (err) => {
        console.error(err);
        this.openSnackBar('Ops something went wrong!', "account");
      }
    });
    this.countries = this.authenticationService.getCountries();
  }

  OnSaveClick(): void {
    if (this.userInfoForm.valid) {
      this.account.firstName = this.userInfoForm.value.firstname;
      this.account.lastName = this.userInfoForm.value.lastname;
      if (this.account.address === undefined) this.account.address = {};
      this.account.address.city = this.userInfoForm.value.city;
      this.account.address.country = this.userInfoForm.value.country;
      this.account.address.address = this.userInfoForm.value.address;
      this.account.address.zipCode = this.userInfoForm.value.zipcode;
      this.account.phone = this.userInfoForm.value.phone;

      this.accountService.updateUser(this.account).subscribe({
        next: () => {
          this.isDisabled = true;
          this.toggleFormState();
          this.openSnackBar('User information changed successfully!', "account");
        },
        error: err => {
          this.openSnackBar('Ops something went wrong!', "account");
          console.error(err);
        }
      })
    }
  }

  OnEditClick(): void {
    this.isDisabled = false;
    this.toggleFormState();
  }

  OnPasswordChangeClick(): void {
    const dialogRef = this.dialog.open(PasswordChangeDialogComponent, {
      data: {'password': ''}
    });
    dialogRef.afterClosed().subscribe((result) => {
      const password = result.password;
      this.accountService.updatePassword(this.account.id, password).subscribe({
        next: (value: string) => {
          this.isDisabled = true;
          this.toggleFormState();
          this.openSnackBar('Password changed successfully!', "account");
        },
        error: err => {
          console.error(err);
          this.openSnackBar('Ops something went wrong!', "account");
        }
      });
    });
  }

  OnDeleteAccountClick(): void {
    this.dialog.open(AccountDeleteDialogComponent).afterClosed().subscribe({
      next: (value: boolean) => {
        if (value) {
          this.accountService.deleteAccount(this.account.id).subscribe({
            next: (value: string) => {
              this.openSnackBar(value, "");
            },
            error: (err) => {
              console.log(err);
              this.openSnackBar(err.error, "account");
            }
          });
        }
      }
    });

  }

  toggleFormState(): void {
    this.userInfoForm.updateValueAndValidity();
    const state = this.isDisabled ? 'disable' : 'enable';
    Object.keys(this.userInfoForm.controls).forEach((controlName: string) => {
      this.userInfoForm.controls[controlName][state]();
    });
  }

  setFormData(): void {
    this.userInfoForm.get('firstname')?.setValue(this.account.firstName);
    this.userInfoForm.get('lastname')?.setValue(this.account.lastName);
    this.userInfoForm.get('phone')?.setValue(this.account.phone);
    this.userInfoForm.get('address')?.setValue(this.account.address?.address);
    this.userInfoForm.get('country')?.setValue(this.account.address?.country);
    this.userInfoForm.get('city')?.setValue(this.account.address?.city);
    this.userInfoForm.get('zipcode')?.setValue(this.account.address?.zipCode);

  }

  onFileSelected(event: Event): void {
    const selectedFile = (event.target as HTMLInputElement).files?.item(0);
    if (selectedFile && ['image/jpeg', 'image/png'].includes(selectedFile.type)) {
      this.accountService.updateAccountImage(this.authenticationService.getUserId(), selectedFile).subscribe({
        next: () => {
          this.openSnackBar('Account image changed successfully!', "account");
        },
        error: err => {
          console.error(err);
          this.openSnackBar('Ops something went wrong!', "account");
        }
      })
    }
  }

  refreshPage(route: string): Function {
    return () => {
      const router = this.router;
      router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        router.navigate([route]);
      });
    }
  }


  openSnackBar(message: string, route: string): void {
    this.snackbar.openFromComponent(DefaultSnackbarComponent, {
      data: {action: this.refreshPage(route)},
      announcementMessage: message
    })
  }

  OnCancelClick(): void {
    this.isDisabled = true;
    this.toggleFormState();
    this.setFormData();
  }

  OnLogoutClick(): void {
    this.authenticationService.logout();
    this.router.navigate(['']);
  }
}
