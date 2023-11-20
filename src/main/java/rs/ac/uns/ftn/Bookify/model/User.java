package rs.ac.uns.ftn.Bookify.model;

import java.util.List;

public abstract class User {

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private boolean blocked;
	private String phone;
	private List<Notification> notifications;
	private Active active;
	private Address address;

	public Active getActive(){
		return this.active;
	}

	public void setActive(Active active){
		this.active = active;
	}

	public Address getAddress(){
		return this.address;
	}

	public void setAddress(Address address){
		this.address = address;
	}

	public String getEmail() {
 		return this.email; 
	}

	public void setEmail(String email) {
 		this.email = email; 
	}

	public String getPassword() {
 		return this.password; 
	}

	public void setPassword(String password) {
 		this.password = password; 
	}

	public String getFirstName() {
 		return this.firstName; 
	}

	public void setFirstName(String firstName) {
 		this.firstName = firstName; 
	}

	public String getLastName() {
 		return this.lastName; 
	}

	public void setLastName(String lastName) {
 		this.lastName = lastName; 
	}

	public boolean getBlocked() {
 		return this.blocked; 
	}

	public void setBlocked(boolean blocked) {
 		this.blocked = blocked; 
	}

	public String getPhone() {
 		return this.phone; 
	}

	public void setPhone(String phone) {
 		this.phone = phone; 
	}

	public List<Notification> getNotifications(){
		return this.notifications;
	}

	public void setNotifications(List<Notification> notifications){
		this.notifications = notifications;
	}

}
