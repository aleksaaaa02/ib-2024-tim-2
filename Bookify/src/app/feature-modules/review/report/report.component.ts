import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent {
  form: FormGroup;
  submitted: boolean;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      comment: ['', Validators.required],
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.valid) {
      console.log("AAA");
    }
  }
}
