import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AccommodationService } from '../../accommodation.service';
import { PriceListDTO } from '../../model/priceList.dto.model';
import { DatePipe } from '@angular/common';
import { PriceList } from '../../model/priceList.model';

@Component({
  selector: 'app-accommodation-price-list-items',
  templateUrl: './accommodation-price-list-items.component.html',
  styleUrl: './accommodation-price-list-items.component.css',
  providers: [DatePipe]
})
export class AccommodationPriceListItemsComponent implements OnInit, AfterViewInit {
  selectedPriceList: PriceList;
  shouldEdit: boolean;
  priceListItems!: PriceList[];
  dataSource!: MatTableDataSource<PriceList>;
  displayedColumns: string[] = ['formattedStartDate', 'formattedEndDate', 'price', 'edit', 'delete'];

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;


  constructor(private service: AccommodationService, private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.getPriceList();
  }

  ngAfterViewInit(): void {
    // this.dataSource.paginator = this.paginator;
    // this.dataSource.sort = this.sort;
  }

  getPriceList() {
    this.service.getAllPriceListItems(1).subscribe({
      next: (data: PriceList[]) => {
        this.priceListItems = data;
        this.priceListItems.forEach(item => {
          if (item.startDate) {
            item.formattedStartDate = this.datePipe.transform(item.startDate, 'yyyy-MM-dd') || '';
          }
          if (item.endDate) {
            item.formattedEndDate = this.datePipe.transform(item.endDate, 'yyyy-MM-dd') || '';
          }
        });
        this.dataSource = new MatTableDataSource<PriceList>(this.priceListItems);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (_) => { console.log("GRESKA!") }
    });
  }

  onEditClicked(priceItem: PriceList) {
    this.shouldEdit = true;
    this.selectedPriceList = priceItem;
  }

  deletePrice(element: PriceList): void {
    this.service.deletePriceListItem(1, element).subscribe({
      next: (_) => {
        this.getPriceList();
      }
    })
  }

  shouldEdited(shouldE: boolean) {
    this.shouldEdit = shouldE;
  }
}
