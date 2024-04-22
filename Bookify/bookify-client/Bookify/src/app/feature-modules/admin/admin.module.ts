import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CertificatesComponent } from './certificates/certificates.component';
import { MaterialModule } from '../../infrastructure/material/material.module';
import { CertificateNodeComponent } from './certificate-node/certificate-node.component';
import { CertificateRequestDetailsComponent } from './certificate-request-details/certificate-request-details.component';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { CertificateDetailsComponent } from './certificate-details/certificate-details.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    CertificatesComponent,
    CertificateNodeComponent,
    CertificateRequestDetailsComponent,
    AddCertificateComponent,
    CertificateDetailsComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule
  ],
  exports: [
    CertificatesComponent,
    CertificateNodeComponent
  ]
})
export class AdminModule { }
