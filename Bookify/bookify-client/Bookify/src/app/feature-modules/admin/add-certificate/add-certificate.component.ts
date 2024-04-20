import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrl: './add-certificate.component.css'
})
export class AddCertificateComponent {

  @Output() submitEvent = new EventEmitter<any>();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<AddCertificateComponent>) { }

  parentSerialNumber: number;

  ngOnInit(): void {
    this.parentSerialNumber = this.data.parentSerialNumber;
    if (this.parentSerialNumber === undefined) {
      this.parentSerialNumber = 0;
    }
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  onAddClick(): void {
    const newCertificate = { name: this.data.name, key: this.data.key };
    this.submitEvent.emit(newCertificate);
    this.dialogRef.close();
  }

}
