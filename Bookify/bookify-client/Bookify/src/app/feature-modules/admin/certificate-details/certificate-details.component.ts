import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CertificateRequestDetailsComponent } from '../certificate-request-details/certificate-request-details.component';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrl: './certificate-details.component.css'
})
export class CertificateDetailsComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<CertificateRequestDetailsComponent>) { }

  closeDialog(): void {
    this.dialogRef.close();
  }

  extensions = [
    {
      name: 'Basic Constraints',
      value: ['CA:FALSE']
    },
    {
      name: 'Key Usage',
      value: ['Digital Signature', 'Key Encipherment']
    },
    {
      name: 'Extended Key Usage',
      value: ['TLS Web Server Authentication', 'TLS Web Client Authentication']
    },
    {
      name: 'Subject Alternative Name',
      value: ['DNS:www.example.com', 'DNS:example.com', 'DNS:example.com', 'DNS:example.com']
    }
  ];

}
