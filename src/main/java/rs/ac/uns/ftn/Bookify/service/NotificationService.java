package rs.ac.uns.ftn.Bookify.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;
import rs.ac.uns.ftn.Bookify.enumerations.NotificationType;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService implements INotificationService {
    @Override
    public Collection<NotificationDTO> getUserNotification(Long userId) {
        Collection<NotificationDTO> notifications = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            notifications.add(new NotificationDTO(1L, "Somebody did something proudly", NotificationType.NEW_USER_RATING));
        }
        return notifications;
    }

    @Override
    public boolean removeNotification(Long userId, Long notificationId) {
        return false;
    }

    @Override
    public NotificationSettingsDTO getNotificationSettings(Long userId) {
        NotificationSettingsDTO settings = new NotificationSettingsDTO();
        Map<NotificationType, Boolean> t = new HashMap<>();
        t.put(NotificationType.NEW_USER_RATING, true);
        t.put(NotificationType.NEW_ACCOMMODATION_RATING, true);
        t.put(NotificationType.RESERVATION_CREATED, true);
        t.put(NotificationType.RESERVATION_CANCELED, true);
        settings.setNotificationType(t);
        return settings;

    }

    @Override
    public NotificationSettingsDTO updateNotificationSettings(Long userId, NotificationSettingsDTO updatedSettings) {
        updatedSettings.getNotificationType().put(NotificationType.NEW_USER_RATING, false);
        return updatedSettings;
    }
}
