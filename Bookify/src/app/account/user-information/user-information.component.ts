import {AfterViewInit, Component, Input, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from "../../feature-modules/authentication/authentication.service";
import {AccountService} from "../account.service";
import {Account} from "../model/account";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {File} from "buffer";

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
              private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    console.log(this.account.imageId)
    this.accountService.getUser(1231).subscribe({
      next: (data: Account) => {
        this.account = data;
        this.setFormData();
        this.userInfoForm.updateValueAndValidity();
        this.accountService.getAccountImage(1).subscribe({
          next: (data: Blob): void => {
            console.log(data);
            const reader = new FileReader();
            reader.onloadend = () => {
              this.image = reader.result;
            }
            reader.readAsDataURL(data);
          },
          error: err => {
            console.error(err)
          }
        })
      },
      error: (_) => {
        console.log("Error occurred!")
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
      // this.account.address.country = this.userInfoForm.value.country;
      this.account.address.address = this.userInfoForm.value.address;
      this.account.address.zipCode = this.userInfoForm.value.zipcode;
      this.account.phone = this.userInfoForm.value.phone;

      this.accountService.updateUser(this.account.id, this.account).subscribe({
        next: value => {
          this.isDisabled = true;
          this.toggleFormState();
        },
        error: err => {
          alert(err);
        }
      })
    }
    console.log(this.userInfoForm);
  }

  OnEditClick(): void {
    this.isDisabled = false;
    this.toggleFormState();
    console.log(this.isDisabled);

  }

  OnPasswordChangeClick(): void {

  }

  OnDeleteAccountClick(): void {

  }

  toggleFormState(): void {
    this.userInfoForm.updateValueAndValidity();
    const state = this.isDisabled ? 'disable' : 'enable';
    Object.keys(this.userInfoForm.controls).forEach((controlName: string) => {
      this.userInfoForm.controls[controlName][state]();
    });
  }

  setFormData(): void {
    console.log(this.account);
    this.userInfoForm.get('firstname')?.setValue(this.account.firstName);
    this.userInfoForm.get('lastname')?.setValue(this.account.lastName);
    this.userInfoForm.get('phone')?.setValue(this.account.phone);
    this.userInfoForm.get('address')?.setValue(this.account.address?.address);
    this.userInfoForm.get('country')?.setValue(this.account.address?.country);
    this.userInfoForm.get('city')?.setValue(this.account.address?.city);
    this.userInfoForm.get('zipcode')?.setValue(this.account.address?.zipCode);
    console.log(this.userInfoForm);

  }

  onFileSelected(event: Event): void {
    let selectedFile = (event.target as HTMLInputElement).files?.item(0);

    if (selectedFile && ['image/jpeg', 'image/png'].includes(selectedFile.type)) {
      this.accountService.updateAccountImage(5, selectedFile).subscribe({
        next: (data: number) => {
          console.log(data);
        },
        error: err => {console.log(err);}
      })
    }
  }
}
