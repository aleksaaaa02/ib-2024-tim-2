interface Extension {
  name: string;
  value: string[];
}

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
  certificatePurpose: string;
  extensions?: Extension[];
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
import { Component, Inject } from '@angular/core';
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

  parentId: number;
  types = ['Custom', 'Digital signature', 'Intermediate certificate authority', 'HTTPS'];
  DIGITAL_SIGNATURE: boolean;
  NON_REPUDIATION: boolean;
  KEY_ENCIPHERMENT: boolean;
  DATA_ENCIPHERMENT: boolean;
  KEY_AGREEMENT: boolean;
  KEY_CERT_SIGN: boolean;
  CRL_SIGN: boolean;
  ENCIPHER_ONLY: boolean;
  DECIPHER_ONLY: boolean;

  SERVER_AUTHENTICATION: boolean;
  CLIENT_AUTHENTICATION: boolean;
  CODE_SIGNING: boolean;
  EMAIL: boolean;
  TIMESTAMPING: boolean;
  OCSP_SIGNING: boolean;
  
  ngOnInit(): void {
    this.parentId = this.data.parentId;
    if (this.parentId === undefined) {
      this.parentId = 0;
    }

    this.data.ca = "true";

    this.httpClient.get<Certificate>(environment.http + 'localhost:8083/api/certificate/' + this.parentId).subscribe((parentCertificate) => {
      this.data.parentName = parentCertificate.subject;
      this.data.parentValidTo = parentCertificate.dateTo;
    });

  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  getKeyUsages(): string[] {
    let usages: string[] = [];
    if (this.DIGITAL_SIGNATURE) {
      usages.push("DIGITAL_SIGNATURE");
    }
    if (this.NON_REPUDIATION) {
      usages.push("NON_REPUDIATION");
    }
    if (this.KEY_ENCIPHERMENT) {
      usages.push("KEY_ENCIPHERMENT");
    }
    if (this.DATA_ENCIPHERMENT) {
      usages.push("DATA_ENCIPHERMENT");
    }
    if (this.KEY_AGREEMENT) {
      usages.push("KEY_AGREEMENT");
    }
    if (this.KEY_CERT_SIGN) {
      usages.push("KEY_CERT_SIGN");
    }
    if (this.CRL_SIGN) {
      usages.push("CRL_SIGN");
    }
    if (this.ENCIPHER_ONLY) {
      usages.push("ENCIPHER_ONLY");
    }
    if (this.DECIPHER_ONLY) {
      usages.push("DECIPHER_ONLY");
    }
    return usages;
  }

  getExtendedKeyUsages(): string[] {
    let usages: string[] = [];
    if (this.SERVER_AUTHENTICATION) {
      usages.push("SERVER_AUTHENTICATION");
    }
    if (this.CLIENT_AUTHENTICATION) {
      usages.push("CLIENT_AUTHENTICATION");
    }
    if (this.CODE_SIGNING) {
      usages.push("CODE_SIGNING");
    }
    if (this.EMAIL) {
      usages.push("EMAIL");
    }
    if (this.TIMESTAMPING) {
      usages.push("TIMESTAMPING");
    }
    if (this.OCSP_SIGNING) {
      usages.push("OCSP_SIGNING");
    }
    return usages;
  }

  onAddClick(): void {
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    if (this.data.dateFrom < currentDate) {
      this.data.dateFrom = currentDate;
    }

    if (this.data.type === "Custom" && this.data.subjectAlternativeName === undefined) {
      this.data.subjectAlternativeName = '';
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
      extensions: [
        {name: "BASIC_CONSTRAINTS", value: this.data.ca},
        {name: "KEY_USAGE", value: this.getKeyUsages()},
        {name: "EXTENDED_KEY_USAGE", value: this.getExtendedKeyUsages()},
        {name: "SUBJECT_ALTERNATIVE_NAME", value: this.data.subjectAlternativeName}
      ],
      certificatePurpose: this.data.type
    };
    this.dialogRef.close(newCertificate);
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
    if (this.data.type === undefined){
      return false;
    }
    if (this.data.type === 'HTTPS') {
      return this.data.subjectAlternativeName !== undefined && this.data.subjectAlternativeName !== '';
    }
    if (this.data.type === 'Custom') {
      if (!this.DIGITAL_SIGNATURE && !this.NON_REPUDIATION 
        && !this.KEY_ENCIPHERMENT && !this.DATA_ENCIPHERMENT 
        && !this.KEY_AGREEMENT && !this.KEY_CERT_SIGN 
        && !this.CRL_SIGN && !this.ENCIPHER_ONLY && !this.DECIPHER_ONLY) {
          return false;
      }
    }
    return true;
  }

}
