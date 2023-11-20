package rs.ac.uns.ftn.Bookify.model;

import java.time.LocalDate;


public class Reservation {

	private LocalDate created;
	private LocalDate start;
	private LocalDate end;
	private int guestNumber;
	private Guest guest;
	private Accommodation accommodation;
	private Status status;

	public Status getStatus(){
		return this.status;
	}

	public void setStatus(Status status){
		this.status = status;
	}

	public Guest getGuest(){
		return this.guest;
	}

	public void setGuest(Guest guest){
		this.guest = guest;
	}

	public Accommodation getAccommodation(){
		return this.accommodation;
	}

	public void setAccommodation(Accommodation accommodation){
		this.accommodation = accommodation;
	}

	public LocalDate getCreated() {
 		return this.created; 
	}

	public void setCreated(LocalDate created) {
 		this.created = created; 
	}

	public LocalDate getStart() {
 		return this.start; 
	}

	public void setStart(LocalDate start) {
 		this.start = start; 
	}

	public LocalDate getEnd() {
 		return this.end; 
	}

	public void setEnd(LocalDate end) {
 		this.end = end; 
	}

	public int getGuestNumber() {
 		return this.guestNumber; 
	}

	public void setGuestNumber(int guestNumber) {
 		this.guestNumber = guestNumber; 
	}
}
