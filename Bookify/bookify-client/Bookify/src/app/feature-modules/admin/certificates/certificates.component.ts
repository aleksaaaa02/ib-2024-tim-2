interface Certificate {
  id: number;
  name: string;
  isEE: boolean;
  children: Certificate[];
}

interface CertificateRequest {
  id: number;
  name: string;
}

interface CertificateRequestDTO {
  id: number;
  issuer: string;
  subject: string;
  subjectEmail: string;
  publicKey: string;
}

import { Component } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from "@angular/router";
import { CertificateRequestDetailsComponent } from '../certificate-request-details/certificate-request-details.component';
import { AddCertificateComponent } from '../add-certificate/add-certificate.component';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrl: './certificates.component.css'
})
export class CertificatesComponent {

  constructor(private authenticationService: AuthenticationService,
    public dialog: MatDialog,
    private router: Router) {

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
        "name": "Certificate Request 1"
    },
    {
        "id": 2,
        "name": "Certificate Request 2"
    },
    {
        "id": 3,
        "name": "Certificate Request 3"
    }
  ];
  certificates: Certificate[] = [
    {
        "id": 1,
        "name": "CERT A",
        "isEE": false,
        "children": [
            {
                "id": 2,
                "name": "CERT A.1",
                "isEE": false,
                "children": []
            },
            {
                "id": 3,
                "name": "EE CERT A.2",
                "isEE": true,
                "children": []
            }
        ]
    },
    {
        "id": 4,
        "name": "CERT B",
        "isEE": false,
        "children": [
            {
                "id": 5,
                "name": "CERT B.1",
                "isEE": false,
                "children": [
                    {
                        "id": 6,
                        "name": "EE CERT B.1.1",
                        "isEE": true,
                        "children": []
                    }
                ]
            }
        ]
    },
    {
        "id": 7,
        "name": "CERT C",
        "isEE": false,
        "children": []
    }
  ];

  ngOnInit(): void {
    // console.log(this.requests);
  }

  selectRequestAndShowDetails(event: any, request: CertificateRequest): void {
    const selectedRequests = document.getElementsByClassName('selected');
    if (selectedRequests.length > 0) {
      selectedRequests[0].classList.remove('selected');
    }
    event.target.classList.add('selected');
    this.selectedRequest = request;

    // TODO: Open dialog with certificate request details
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
      }).afterClosed().subscribe((result) => {
        if (result) {
          // Update the list with the submitted request
          // this.list.push(result);
        }
      });
  }

  signCertificate(): void {
    if (this.selectedRequest === undefined || this.selectedRequest === null ||
      this.selectedCertificate === undefined || this.selectedCertificate === null) {
      alert("Please select a certificate request and a certificate to sign with.");
      return;
    }

  }

  rejectCertificate(): void {
  
  }

  selectCertificate(certificate: Certificate): void {
    this.selectedCertificate = certificate;
  }

}
