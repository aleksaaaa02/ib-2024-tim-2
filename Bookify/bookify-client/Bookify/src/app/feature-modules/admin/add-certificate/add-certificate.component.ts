interface CertificateDTO {
  id?: number;
  issuer: string;
  subject: string;
  dateFrom: Date;
  dateTo: Date;
  type: string;
  isEE?: boolean;
}

import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrl: './add-certificate.component.css'
})
export class AddCertificateComponent {

  @Output() submitEvent = new EventEmitter<any>();

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

    this.data.parentValidTo = new Date(2024, 3, 22); // za testiranje

    this.httpClient.get<CertificateDTO>('localhost:8083/api/certificate/' + this.parentId).subscribe((parentCertificate) => {
      this.data.parentName = parentCertificate.subject;
      this.data.parentValidTo = parentCertificate.dateTo;
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
      issuer: this.data.parentName,
      subject: this.data.subjectName,
      dateFrom: this.data.dateFrom,
      dateTo: this.data.dateTo,
      type: this.data.type === 'Intermediate' ? 'INTERMEDIATE' : 'END_ENTITY'
    };
    this.submitEvent.emit(newCertificate);
    this.dialogRef.close();
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
