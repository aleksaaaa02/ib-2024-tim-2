package rs.ac.uns.ftn.Bookify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.NotificationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	private Long id;
	private String description;
	private NotificationType notificationType;

}
