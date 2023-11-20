package rs.ac.uns.ftn.Bookify.model;

import java.util.List;
import java.util.Map;

public class Guest extends User {

    private Map<NotificationType, Boolean> notificationType;
    private List<Accommodation> favorites;

    public List<Accommodation> getFavorites(){
        return this.favorites;
    }

    public void setFavorites(List<Accommodation> favorites){
        this.favorites = favorites;
    }

    public Map<NotificationType, Boolean> getNotificationType(){
        return this.notificationType;
    }

    public void setNotificationType(Map<NotificationType, Boolean> notificationType){
        this.notificationType = notificationType;
    }
}
