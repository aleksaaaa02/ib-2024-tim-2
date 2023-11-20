package rs.ac.uns.ftn.Bookify.model;

import java.time.LocalDate;

public class Availability {

	private LocalDate startDate;
	private LocalDate endDate;

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

}
