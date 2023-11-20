package rs.ac.uns.ftn.Bookify.model;

public class Notification {

	private String description;
	private NotificationType notificationType;

	public NotificationType getNofNotificationType(){
		return this.notificationType;
	}

	public void setNotificationType(NotificationType notificationType){
		this.notificationType = notificationType;
	}

	public String getDescription() {
 		return this.description; 
	}

	public void setDescription(String description) {
 		this.description = description; 
	}
}
