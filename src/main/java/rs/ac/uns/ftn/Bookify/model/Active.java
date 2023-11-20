package rs.ac.uns.ftn.Bookify.model;

import java.time.LocalDateTime;

public class Active {

	private boolean isActive;
	private LocalDateTime time;

	public boolean getIsActive() {
 		return this.isActive; 
	}

	public void setIsActive(boolean isActive) {
 		this.isActive = isActive; 
	}

	public LocalDateTime getTime() {
 		return this.time; 
	}

	public void setTime(LocalDateTime time) {
 		this.time = time; 
	}
}
