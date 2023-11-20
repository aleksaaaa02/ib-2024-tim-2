package rs.ac.uns.ftn.Bookify.model;

import java.time.LocalDate;

public class PricelistItem {

	private LocalDate startDate;
	private LocalDate endDate;
	private double price;

	public LocalDate getStartDate() {
 		return this.startDate; 
	}

	public void setStartDate(LocalDate startDate) {
 		this.startDate = startDate; 
	}

	public LocalDate getEndDate() {
 		return this.endDate; 
	}

	public void setEndDate(LocalDate endDate) {
 		this.endDate = endDate; 
	}

	public double getPrice() {
 		return this.price; 
	}

	public void setPrice(double price) {
 		this.price = price; 
	}

}
