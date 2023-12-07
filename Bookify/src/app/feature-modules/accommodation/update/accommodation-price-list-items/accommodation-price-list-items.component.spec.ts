import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationPriceListItemsComponent } from './accommodation-price-list-items.component';

describe('AccommodationPriceListItemsComponent', () => {
  let component: AccommodationPriceListItemsComponent;
  let fixture: ComponentFixture<AccommodationPriceListItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationPriceListItemsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationPriceListItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
