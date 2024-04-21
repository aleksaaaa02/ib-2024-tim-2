interface CertificateDTO {
  issuerId: number;
  commonName: string;
  organization: string;
  organizationalUnit: string;
  email: string;
  locality: string;
  country: string;
  notBefore: Date;
  notAfter: Date;
  certificateType: string;
  purpose: string;

  subject?: string;
  isEE?: boolean;
}

interface Certificate {
  id: number;
  issuer: string;
  subject: string;
  dateFrom: Date;
  dateTo: Date;
  certificatePurpose: string;
  isEE: boolean;
  children?: Certificate[];
}

import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { environment } from '../../../../env/env';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrl: './add-certificate.component.css'
})
export class AddCertificateComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AddCertificateComponent>,
    private httpClient: HttpClient) { }

  types = ['Intermediate', 'End entity'];
  parentId: number;

  ngOnInit(): void {
    this.parentId = this.data.parentId;
    if (this.parentId === undefined) {
      this.parentId = 0;
    }

    // this.data.parentValidTo = new Date(2024, 3, 22); // za testiranje

    this.httpClient.get<Certificate>(environment.http + 'localhost:8083/api/certificate/' + this.parentId).subscribe((parentCertificate) => {
      this.data.parentName = parentCertificate.subject;
      this.data.parentValidTo = parentCertificate.dateTo;
      console.log(this.data.parentValidTo);
    });

  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  onAddClick(): void {
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    if (this.data.dateFrom < currentDate) {
      this.data.dateFrom = currentDate;
    }

    const newCertificate: CertificateDTO = {
      issuerId: this.parentId,
      commonName: this.data.commonName,
      organization: this.data.organization,
      organizationalUnit: this.data.organizationalUnit,
      email: this.data.email,
      locality: this.data.locality,
      country: this.data.country,
      notBefore: this.data.dateFrom,
      notAfter: this.data.dateTo,
      certificateType: this.data.type === 'Intermediate' ? 'INTERMEDIATE' : 'END_ENTITY',
      purpose: this.data.type === 'Intermediate' ? 'INTERMEDIATE_CERTIFICATE_AUTHORITY' : 'END_ENTITY'
      // subject: this.data.subjectName,
    };
    this.dialogRef.close(newCertificate);
    // this.dialogRef.close();
  }

  checkDatesValidity() {
    if (this.data.dateFrom && this.data.dateTo) {
      return this.data.dateTo >= this.data.dateFrom;
    }
    return false;
  }

  checkSubjectValidity() {
    return this.data.commonName !== undefined && this.data.commonName !== '';
  }

  checkTypeValidity() {
    return this.data.type !== undefined;
  }

}
