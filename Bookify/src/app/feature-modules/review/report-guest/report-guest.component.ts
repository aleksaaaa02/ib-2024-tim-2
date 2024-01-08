import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-report-guest',
  templateUrl: './report-guest.component.html',
  styleUrl: './report-guest.component.css'
})
export class ReportGuestComponent {
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
