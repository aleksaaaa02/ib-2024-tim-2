package rs.ac.uns.ftn.Bookify.model;

import java.time.LocalDate;


public class Rating {

	private double rate;
	private String comment;
	private LocalDate date;
	private boolean accepted;
	private boolean reported;
	private Guest guest;

	public void setGuest(Guest guest){
		this.guest = guest;
	}

	public Guest getGuest(){
		return this.guest;
	}

	public double getRate() {
 		return this.rate; 
	}

	public void setRate(double rate) {
 		this.rate = rate; 
	}

	public String getComment() {
 		return this.comment; 
	}

	public void setComment(String comment) {
 		this.comment = comment; 
	}

	public LocalDate getDate() {
 		return this.date; 
	}

	public void setDate(LocalDate date) {
 		this.date = date; 
	}

	public boolean getReported() {
 		return this.reported; 
	}

	public void setReported(boolean reported) {
 		this.reported = reported; 
	}

	public boolean getAccepted() {
 		return this.accepted; 
	}

	public void setAccepted(boolean accepted) {
 		this.accepted = accepted; 
	}
}
