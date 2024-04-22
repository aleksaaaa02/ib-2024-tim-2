import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CertificateRequestDetailsComponent } from '../certificate-request-details/certificate-request-details.component';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrl: './certificate-details.component.css'
})
export class CertificateDetailsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<CertificateRequestDetailsComponent>) { }

  closeDialog(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.data.certificate.extensions.forEach((element: { extensionsType: string; value: string[]; }) => {
      if (element.extensionsType === 'BASIC_CONSTRAINTS') {
        this.data.certificate.extensions[this.data.certificate.extensions.indexOf(element)].extensionsType = 'Basic Constraints';
        this.data.certificate.extensions[this.data.certificate.extensions.indexOf(element)].value = ["CA:" + element.value];
      }
      if (element.extensionsType === 'KEY_USAGE') {
        this.data.certificate.extensions[this.data.certificate.extensions.indexOf(element)].extensionsType = 'Key Usage';
      }
      if (element.extensionsType === 'EXTENDED_KEY_USAGE') {
        this.data.certificate.extensions[this.data.certificate.extensions.indexOf(element)].extensionsType = 'Extended Key Usage';
      }
      if (element.extensionsType === 'SUBJECT_ALTERNATIVE_NAME') {
        this.data.certificate.extensions[this.data.certificate.extensions.indexOf(element)].extensionsType = 'Subject Alternative Name';
      }
    });
  }
}
