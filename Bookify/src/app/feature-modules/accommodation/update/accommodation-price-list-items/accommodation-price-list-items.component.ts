import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AccommodationService } from '../../accommodation.service';

@Component({
  selector: 'app-accommodation-price-list-items',
  templateUrl: './accommodation-price-list-items.component.html',
  styleUrl: './accommodation-price-list-items.component.css'
})
export class AccommodationPriceListItemsComponent implements OnInit {
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = ['startDate', 'endDate', 'price'];

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;

  constructor(private service: AccommodationService) {

  }

  ngOnInit(): void {
    // this.service
  }
}
