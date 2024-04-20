interface Certificate {
  id: number;
  name: string;
  isEE: boolean;
  children: Certificate[];
}

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddCertificateComponent } from '../add-certificate/add-certificate.component';
import { CertificateDetailsComponent } from '../certificate-details/certificate-details.component';

@Component({
  selector: 'app-certificate-node',
  templateUrl: './certificate-node.component.html',
  styleUrl: './certificate-node.component.css'
})
export class CertificateNodeComponent {

  @Input() certificate: Certificate;
  @Output() selectedCertificate = new EventEmitter<Certificate>();
  isExpanded: boolean = false;

  constructor(public dialog: MatDialog) { }

  toggleChildren(event: any): void {
    this.isExpanded = !this.isExpanded;
    event.stopPropagation();
  }

  addChild(event: any): void {
    event.stopPropagation();
    this.dialog.open(AddCertificateComponent, {
      width: '500px',
      height: '500px',
      data: { parentSerialNumber: this.certificate.id }
    }).afterClosed().subscribe((result) => {
      if (result) {
        // Update the list with the submitted request
        // this.list.push(result);
      }
    });
  }

  deleteNode(event: any): void {
    event.stopPropagation();

  }

  openDetailsAndSelect(event: any): void {
    const selectedRequests = document.getElementsByClassName('selectedNode');
    if (selectedRequests.length > 0) {
      selectedRequests[0].classList.remove('selectedNode');
    }
    event.target.classList.add('selectedNode');

    this.selectedCertificate.emit(this.certificate);

    // TODO: Open dialog with certificate details
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
