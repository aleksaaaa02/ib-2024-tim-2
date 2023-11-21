package rs.ac.uns.ftn.Bookify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportedUser {

	private Long id;
	private String reason;
	private LocalDateTime created;
	private User reportedUser;
	private User createdBy;

}
