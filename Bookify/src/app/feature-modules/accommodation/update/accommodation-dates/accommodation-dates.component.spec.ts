import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationDatesComponent } from './accommodation-dates.component';

describe('AccommodationDatesComponent', () => {
  let component: AccommodationDatesComponent;
  let fixture: ComponentFixture<AccommodationDatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationDatesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationDatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
