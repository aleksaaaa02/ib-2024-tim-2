import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AccommodationBasicFormModel } from '../../model/accommodation-basic.form.model';

@Component({
  selector: 'app-accommodation-basic-information',
  templateUrl: './accommodation-basic-information.component.html',
  styleUrl: './accommodation-basic-information.component.css'
})
export class AccommodationBasicInformationComponent implements OnChanges {
  @Output() basicInfoChanged = new EventEmitter<AccommodationBasicFormModel>();
  @Output() validationStatus: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() submitted: boolean = false;

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      propertyName: ['', Validators.required],
      description: ['', Validators.required],
    });
    this.form.valueChanges.subscribe(value => {
      this.onBasicInfoChange();
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.submitted) {
      this.form.markAllAsTouched();
    }
  }

  onBasicInfoChange() {
    this.basicInfoChanged.emit({
      propertyName: this.form.get('propertyName')?.value,
      description: this.form.get('description')?.value,
    });
  }

  validateForm() {
    const isValid = this.form.get('propertyName')?.value.trim().length > 0 && this.form.get('description')?.value.trim().length > 0;
    this.validationStatus.emit(isValid);
    return isValid;
  }
}
