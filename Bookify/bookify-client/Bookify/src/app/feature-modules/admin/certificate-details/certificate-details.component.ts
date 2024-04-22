import {AfterViewChecked, Component, Inject, OnInit} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CertificateRequestDetailsComponent } from '../certificate-request-details/certificate-request-details.component';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrl: './certificate-details.component.css'
})
export class CertificateDetailsComponent implements AfterViewChecked {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<CertificateRequestDetailsComponent>) { }

  closeDialog(): void {
    this.dialogRef.close();
  }

  ngAfterViewChecked(): void {
    console.log(this.data);
    this.data.extensions.forEach((element: { extensionsType: string; value: string[]; }) => {
      if (element.extensionsType === 'BASIC_CONSTRAINTS') {
        this.data.extensions[this.data.extensions.indexOf(element)].name = 'Basic Constraints';
        this.data.extensions[this.data.extensions.indexOf(element)].value = "CA:" + element.value[0];
      }
      if (element.extensionsType === 'KEY_USAGE') {
        this.data.extensions[this.data.extensions.indexOf(element)].name = 'Key Usage';
      }
      if (element.extensionsType === 'EXTENDED_KEY_USAGE') {
        this.data.extensions[this.data.extensions.indexOf(element)].name = 'Extended Key Usage';
      }
      if (element.extensionsType === 'SUBJECT_ALTERNATIVE_NAME') {
        this.data.extensions[this.data.extensions.indexOf(element)].name = 'Subject Alternative Name';
      }
    });
  }
}
