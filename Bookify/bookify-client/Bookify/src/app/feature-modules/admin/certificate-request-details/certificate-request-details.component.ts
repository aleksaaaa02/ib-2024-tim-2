import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-certificate-request-details',
  templateUrl: './certificate-request-details.component.html',
  styleUrl: './certificate-request-details.component.css'
})
export class CertificateRequestDetailsComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<CertificateRequestDetailsComponent>) { }

  closeDialog(): void {
    this.dialogRef.close();
  }

}
