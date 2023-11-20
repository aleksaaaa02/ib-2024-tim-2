package rs.ac.uns.ftn.Bookify.model;

import java.time.LocalDateTime;


public class ReportedUser {

	private String reason;
	private LocalDateTime created;
	private User reportedUser;
	private User createdBy;

	public User getReportedUser(){
		return reportedUser;
	}

	public void setReportedUser(User reportedUser){
		this.reportedUser = reportedUser;
	}

	public User getCreatedBy(){
		return this.createdBy;
	}

	public void setCreatedBy(User createdBy){
		this.createdBy = createdBy;
	}

	public String getReason() {
 		return this.reason; 
	}

	public void setReason(String reason) {
 		this.reason = reason; 
	}

	public LocalDateTime getCreated() {
 		return this.created; 
	}

	public void setCreated(LocalDateTime created) {
 		this.created = created; 
	}

}
