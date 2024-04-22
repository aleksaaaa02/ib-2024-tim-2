interface Extension {
  extensionsType: string;
  value: string[];
}

interface Certificate {
  id: number;
  issuer: string;
  subject: string;
  dateFrom: Date;
  dateTo: Date;
  purpose: string;
  isEE: boolean;
  extensions?: Extension[];
  children?: Certificate[];
}

interface CertificateRequest {
  id: number;
  subjectName: string;
  locality: string;
  country: string;
  email: string;
  certificateType: string;
}

import { Component } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from "@angular/router";
import { CertificateRequestDetailsComponent } from '../certificate-request-details/certificate-request-details.component';
import { AddCertificateComponent } from '../add-certificate/add-certificate.component';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../env/env';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrl: './certificates.component.css'
})
export class CertificatesComponent {

  constructor(private authenticationService: AuthenticationService,
    public dialog: MatDialog,
    private router: Router,
    private httpClient: HttpClient) {

  }

  logout(): void {
    this.authenticationService.logout();
    this.router.navigate(['']);
  }

  selectedRequest: CertificateRequest;
  selectedCertificate: Certificate;

  requests: CertificateRequest[] = [];
  certificates: Certificate[] = [];

  ngOnInit(): void {
    this.httpClient.get<CertificateRequest[]>(environment.http + 'localhost:8083/api/certificate/request/all').subscribe((data) => {
      this.requests = data;
    });
    this.httpClient.get<Certificate[]>(environment.http + 'localhost:8083/api/certificate/0/signed').subscribe((data) => {
      this.processCertificates(data);
      this.certificates = data;
    });
  }

  processCertificates(certificates: Certificate[]): void {
    certificates.forEach((certificate) => {

      let dateFromString = certificate.dateFrom as unknown as string;
      let dateFromParts = dateFromString.split('-');
      let dateFrom = new Date(+dateFromParts[0], +dateFromParts[1] - 1, +dateFromParts[2]);

      let dateToString = certificate.dateTo as unknown as string;
      let dateToParts = dateToString.split('-');
      let dateTo = new Date(+dateToParts[0], +dateToParts[1] - 1, +dateToParts[2]);

      certificate.dateFrom = dateFrom;
      certificate.dateTo = dateTo;
      if (certificate.extensions)
      certificate.isEE = certificate.extensions?.some((extension) => extension.extensionsType === 'BASIC_CONSTRAINTS' && extension.value.includes('true'));

  if (!certificate.isEE) {

        this.httpClient.get<Certificate[]>(environment.http + `localhost:8083/api/certificate/${certificate.id}/signed`).subscribe((certificateData) => {
          certificate.children = certificateData;
          this.processCertificates(certificate.children);
        });
      }
    });
  }

  selectRequestAndShowDetails(event: any, request: CertificateRequest): void {
    const selectedRequests = document.getElementsByClassName('selected');
    if (selectedRequests.length > 0) {
      selectedRequests[0].classList.remove('selected');
    }
    event.target.classList.add('selected');
    this.selectedRequest = request;

    this.dialog.open(CertificateRequestDetailsComponent, {
      width: '500px',
      height: '490px',
      data: { request: request }
    });
  }

  addCertificate(): void {
      this.dialog.open(AddCertificateComponent, {
        width: '500px',
        height: '520px',
        data: { parentSerialNumber: undefined }
      }).afterClosed().subscribe((newCertificate) => {
        if (newCertificate) {
          this.httpClient.post(environment.http + 'localhost:8083/api/certificate', newCertificate).subscribe((newCertificateWithId) => {
            this.certificates.push(newCertificateWithId as Certificate);
          });
        }
      });
  }

  signCertificate(): void {
    if (this.selectedRequest === undefined || this.selectedRequest === null ||
      this.selectedCertificate === undefined || this.selectedCertificate === null) {
      alert("Please select a certificate request and a certificate to sign with.");
      return;
    }

    if (this.selectedCertificate.purpose === 'DIGITAL_SIGNATURE' || this.selectedCertificate.purpose === 'HTTPS') {
      alert("Cannot sign with an End Entity certificate.");
      return;
    }

    this.httpClient.post(environment.http + `localhost:8083/api/certificate/request/accept/${this.selectedRequest.id}/${this.selectedCertificate.id}`, {}).subscribe(() => {
      this.requests = this.requests.filter((request) => request.id !== this.selectedRequest.id);
      location.reload();
    });
  }

  rejectCertificate(): void {
    if (this.selectedRequest === undefined || this.selectedRequest === null) {
      alert("Please select a certificate request to reject.");
      return;
    }
    this.httpClient.put(environment.http + `localhost:8083/api/certificate/request/reject/${this.selectedRequest.id}`, {});
    this.requests = this.requests.filter((request) => request.id !== this.selectedRequest.id);
  }

  selectCertificate(certificate: Certificate): void {
    this.selectedCertificate = certificate;
  }

}
