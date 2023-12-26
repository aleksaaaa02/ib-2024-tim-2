import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrl: './new-comment.component.css'
})
export class NewCommentComponent {
  percent: number = 0;
  form: FormGroup;
  submitted: boolean;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      comment: ['', Validators.required],
    });
  }

  onRatingClick(event: MouseEvent) {
    const meter = event.target as HTMLMeterElement;
    this.percent = event.offsetX / meter.offsetWidth;
    this.percent *= 5;
    this.percent = Math.ceil(this.percent);
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.valid && this.percent > 0) {
      console.log("AAA");
    }
  }
}
