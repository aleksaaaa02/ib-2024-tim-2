package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;
import rs.ac.uns.ftn.Bookify.mapper.NotificationDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Notification;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationDTOMapper mapper;

    @Autowired
    private INotificationService notificationService;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getUserNotifications(@PathVariable String userId) {
        List<NotificationDTO> notifications = new ArrayList<>();
        notificationService.getUserNotification(userId).forEach(n -> {
            notifications.add(mapper.toNotificationDTO(n));
        });
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(value = "/unseen/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getUnseenUserNotifications(@PathVariable String userId) {
        List<NotificationDTO> notifications = new ArrayList<>();
        notificationService.getUnseenNotifications(userId).forEach(n -> {
            notifications.add(mapper.toNotificationDTO(n));

        });
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}/{notificationId}")
    public ResponseEntity<String> removeNotification(@PathVariable String userId, @PathVariable String notificationId) {
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationSettingsDTO> getNotificationSettings(@PathVariable String userId) {
        Optional<NotificationSettingsDTO> settings = Optional.ofNullable(notificationService.getNotificationSettings(userId));
        return settings.map(notificationSettingsDTO -> new ResponseEntity<>(notificationSettingsDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{userId}/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationSettingsDTO> updateNotificationSettings(@PathVariable String userId, @RequestBody NotificationSettingsDTO updatedSettings) {
        Optional<NotificationSettingsDTO> settings = Optional.ofNullable(notificationService.updateNotificationSettings(userId, updatedSettings));
        return settings.map(notificationSettingsDTO -> new ResponseEntity<>(notificationSettingsDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
