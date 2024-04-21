interface Certificate {
  id: number;
  issuer: string;
  subject: string;
  dateFrom: Date;
  dateTo: Date;
  type: string;
  isEE: boolean;
  children?: Certificate[];
}

interface CertificateRequest {
  id: number;
  subjectName: string;
  locality: string;
  country: string;
  email: string;
  type: string;
}

import { Component } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from "@angular/router";
import { CertificateRequestDetailsComponent } from '../certificate-request-details/certificate-request-details.component';
import { AddCertificateComponent } from '../add-certificate/add-certificate.component';
import { HttpClient } from '@angular/common/http';

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

  requests: CertificateRequest[] = [
    {
      "id": 1,
      "subjectName": "John Doe",
      "locality": "Belgrade",
      "country": "Serbia",
      "email": "johndoe@gmail.com",
      "type": "END_ENTITY"
    },
    {
      "id": 2,
      "subjectName": "Jane Doe",
      "locality": "Belgrade",
      "country": "Serbia",
      "email": "janedoe@gmail.com",
      "type": "END_ENTITY"
    },
    {
      "id": 3,
      "subjectName": "John Smith",
      "locality": "Belgrade",
      "country": "Serbia",
      "email": "johnsmith@gmail.com",
      "type": "END_ENTITY"
    }
  ];
  certificates: Certificate[] = [
    {
      "id": 1,
      "issuer": "Root CA",
      "subject": "CA1",
      "dateFrom": new Date(2022, 0, 1), // 1 Jan 2023
      "dateTo": new Date(2028, 11, 31), // 31 Dec 2023
      "type": "INTERMEDIATE",
      "isEE": false,
      "children": [
        {
          "id": 4,
          "issuer": "CA1",
          "subject": "EE1",
          "dateFrom": new Date(2022, 0, 1),
          "dateTo": new Date(2023, 11, 31),
          "type": "END_ENTITY",
          "isEE": true,
          "children": []
        },
        {
          "id": 5,
          "issuer": "CA1",
          "subject": "CA4",
          "dateFrom": new Date(2022, 0, 1),
          "dateTo": new Date(2023, 11, 31),
          "type": "INTERMEDIATE",
          "isEE": false,
          "children": [
            {
              "id": 6,
              "issuer": "CA4",
              "subject": "EE2",
              "dateFrom": new Date(2022, 0, 1),
              "dateTo": new Date(2023, 11, 31),
              "type": "END_ENTITY",
              "isEE": true,
              "children": []
            }
          ]
        }
      ]
    },
    {
      "id": 2,
      "issuer": "Root CA",
      "subject": "CA2",
      "dateFrom": new Date(2022, 0, 1),
      "dateTo": new Date(2028, 11, 31),
      "type": "INTERMEDIATE",
      "isEE": false,
      "children": [
        {
          "id": 7,
          "issuer": "CA2",
          "subject": "EE3",
          "dateFrom": new Date(2022, 0, 1),
          "dateTo": new Date(2023, 11, 31),
          "type": "END_ENTITY",
          "isEE": true,
          "children": []
        },
        {
          "id": 8,
          "issuer": "CA2",
          "subject": "CA5",
          "dateFrom": new Date(2022, 0, 1),
          "dateTo": new Date(2023, 11, 31),
          "type": "INTERMEDIATE",
          "isEE": false,
          "children": [
            {
              "id": 9,
              "issuer": "CA5",
              "subject": "EE4",
              "dateFrom": new Date(2022, 0, 1),
              "dateTo": new Date(2023, 11, 31),
              "type": "END_ENTITY",
              "isEE": true,
              "children": []
            },
            {
              "id": 10,
              "issuer": "CA5",
              "subject": "EE5",
              "dateFrom": new Date(2022, 0, 1),
              "dateTo": new Date(2023, 11, 31),
              "type": "END_ENTITY",
              "isEE": true,
              "children": []
            }
          ]
        }
      ]
    },
    {
      "id": 3,
      "issuer": "Root CA",
      "subject": "CA3",
      "dateFrom": new Date(2022, 0, 1),
      "dateTo": new Date(2028, 11, 31),
      "type": "INTERMEDIATE",
      "isEE": false,
      "children": [
        {
          "id": 11,
          "issuer": "CA3",
          "subject": "EE6",
          "dateFrom": new Date(2022, 0, 1),
          "dateTo": new Date(2023, 11, 31),
          "type": "END_ENTITY",
          "isEE": true,
          "children": []
        },
        {
          "id": 12,
          "issuer": "CA3",
          "subject": "CA6",
          "dateFrom": new Date(2022, 0, 1),
          "dateTo": new Date(2023, 11, 31),
          "type": "INTERMEDIATE",
          "isEE": false,
          "children": [
            {
              "id": 13,
              "issuer": "CA6",
              "subject": "EE7",
              "dateFrom": new Date(2022, 0, 1),
              "dateTo": new Date(2023, 11, 31),
              "type": "END_ENTITY",
              "isEE": true,
              "children": []
            }
          ]
        }
      ]
    }
  
  ];

  ngOnInit(): void {
    this.httpClient.get<CertificateRequest[]>('localhost:8083/api/certificate/request/all').subscribe((data) => {
      this.requests = data;
    });
    this.httpClient.get<Certificate[]>('localhost:8083/api/certificate/0/signed').subscribe((data) => {
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
      certificate.isEE = certificate.type === 'END_ENTITY';
  
      if (certificate.type !== 'END_ENTITY') {
        this.httpClient.get<Certificate[]>(`localhost:8083/api/certificate/${certificate.id}/signed`).subscribe((certificateData) => {
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
      height: '500px',
      data: { request: request }
    });
  }

  addCertificate(): void {
      this.dialog.open(AddCertificateComponent, {
        width: '500px',
        height: '500px',
        data: { parentSerialNumber: undefined }
      }).afterClosed().subscribe((newCertificate) => {
        if (newCertificate) {
          this.httpClient.post('localhost:8083/api/certificate', newCertificate).subscribe((newCertificateWithId) => {
            this.certificates.push(newCertificateWithId as Certificate);
            // TODO proveri da li ovo radi (da li se azurira na frontu)
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
    this.httpClient.post(`localhost:8083/api/certificate/request/accept/${this.selectedRequest.id}/${this.selectedCertificate.id}`, {});
    this.requests = this.requests.filter((request) => request.id !== this.selectedRequest.id);
    // deselect the certificate
    const selectedRequests = document.getElementsByClassName('selectedNode');
    if (selectedRequests.length > 0) {
      selectedRequests[0].classList.remove('selectedNode');
    }
  }

  rejectCertificate(): void {
    if (this.selectedRequest === undefined || this.selectedRequest === null) {
      alert("Please select a certificate request to reject.");
      return;
    }
    this.httpClient.put(`localhost:8083/api/certificate/request/reject/${this.selectedRequest.id}`, {});
    this.requests = this.requests.filter((request) => request.id !== this.selectedRequest.id);
  }

  selectCertificate(certificate: Certificate): void {
    this.selectedCertificate = certificate;
  }

}
