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

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddCertificateComponent } from '../add-certificate/add-certificate.component';
import { CertificateDetailsComponent } from '../certificate-details/certificate-details.component';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../../env/env';

@Component({
  selector: 'app-certificate-node',
  templateUrl: './certificate-node.component.html',
  styleUrl: './certificate-node.component.css'
})
export class CertificateNodeComponent {

  @Input() certificate: Certificate;
  @Output() selectedCertificate = new EventEmitter<Certificate>();
  isExpanded: boolean = false;

  constructor(public dialog: MatDialog, private httpClient: HttpClient, private router: Router,) { }

  toggleChildren(event: any): void {
    this.isExpanded = !this.isExpanded;
    event.stopPropagation();
  }

  addChild(event: any): void {
    event.stopPropagation();
    this.dialog.open(AddCertificateComponent, {
      width: '500px',
      height: '500px',
      data: { parentId: this.certificate.id }
    }).afterClosed().subscribe((newCertificate) => {
      if (newCertificate) {
        this.httpClient.post(environment.http + 'localhost:8083/api/certificate', newCertificate).subscribe((newCertificateWithId) => {
          if (this.certificate.children) {
            this.certificate.children.push(newCertificateWithId as Certificate);
          } else {
            this.certificate.children = [newCertificateWithId as Certificate];
          }
        });
      }
    });
  }

  deleteNode(event: any): void {
    event.stopPropagation();
    console.log('deleting certificate with id: ' + this.certificate.id);
    this.httpClient.delete(environment.http + 'localhost:8083/api/certificate/' + this.certificate.id).subscribe(() => {
      location.reload(); 
    });
  }

  openDetailsAndSelect(event: any): void {
    const selectedRequests = document.getElementsByClassName('selectedNode');
    if (selectedRequests.length > 0) {
      selectedRequests[0].classList.remove('selectedNode');
    }
    event.target.classList.add('selectedNode');

    this.selectedCertificate.emit(this.certificate);

    this.dialog.open(CertificateDetailsComponent, {
      width: '500px',
      height: '500px',
      data: { certificate: this.certificate }
    });
  }

  selectCertificate(certificate: Certificate): void {
    this.selectedCertificate.emit(certificate);
  }

}
