package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.Bookify.model.Notification;

public interface INotificationRepository extends JpaRepository<Notification, Long> {

}
