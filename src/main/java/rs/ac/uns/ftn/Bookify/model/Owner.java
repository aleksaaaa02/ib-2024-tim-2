package rs.ac.uns.ftn.Bookify.model;

import java.util.List;
import java.util.Map;

public class Owner extends User {

    private Map<NotificationType, Boolean> notificationType;
    private List<Accommodation> accommodations;
    private List<Rating> ratings;

    public void setRatings(List<Rating> ratings){
		this.ratings = ratings;
	}

	public List<Rating> getRatings(){
		return this.ratings;
	}

    public void setAccommodations(List<Accommodation> accommodations){
        this.accommodations = accommodations;
    }

    public List<Accommodation> getAccommodations(){
        return this.accommodations;
    }

    public Map<NotificationType, Boolean> getNotificationType(){
        return this.notificationType;
    }

    public void setNotificationType(Map<NotificationType, Boolean> notificationType){
        this.notificationType = notificationType;
    }
}
