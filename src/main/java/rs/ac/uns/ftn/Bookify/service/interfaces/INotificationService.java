package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;

import java.util.Collection;

public interface INotificationService {
    public Collection<NotificationDTO> getUserNotification(Long userId);
    public boolean removeNotification(Long userId, Long notificationId);
    public NotificationSettingsDTO getNotificationSettings(Long userId);
    public NotificationSettingsDTO updateNotificationSettings(Long userId, NotificationSettingsDTO updatedSettings);
}
