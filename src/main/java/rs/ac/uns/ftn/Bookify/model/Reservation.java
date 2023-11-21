package rs.ac.uns.ftn.Bookify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.Status;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

	private Long id;
	private LocalDate created;
	private LocalDate start;
	private LocalDate end;
	private int guestNumber;
	private Guest guest;
	private Accommodation accommodation;
	private Status status;
}
