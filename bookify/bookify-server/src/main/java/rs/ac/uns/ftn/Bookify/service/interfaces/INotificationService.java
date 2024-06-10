package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;
import rs.ac.uns.ftn.Bookify.model.*;

import java.util.Collection;
import java.util.List;

public interface INotificationService {
    public List<Notification> getUserNotification(String userId);
    public boolean removeNotification(String userId, Long notificationId);
    public NotificationSettingsDTO getNotificationSettings(String userId);
    public NotificationSettingsDTO updateNotificationSettings(String userId, NotificationSettingsDTO updatedSettings);
    public List<Notification> getUnseenNotifications(String userId);
    public void sendNewNotification(Notification notification, String userId);
    public void saveNotification(Notification notification, String userId);
    public Notification createNotificationOwnerNewReservation(Reservation reservation);
    public Notification createNotificationOwnerReservationCancellation(Reservation reservation);
    public Notification createNotificationOwnerGotNewRating(Guest guest, Owner owner);
    public Notification createNotificationOwnerAccommodationGotNewRating(Guest guest, Accommodation accommodation);
    public Notification createNotificationGuestRequestResponse(Reservation reservation);
}
