package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.Status;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date created;

	@Column(nullable = false)
	private Date start;

	@Column(nullable = false)
	private Date end;

	@Column(nullable = false)
	private int guestNumber;

	@ManyToOne
	private Guest guest;

	@ManyToOne
	private Accommodation accommodation;

	@Enumerated(EnumType.STRING)
	private Status status;
}
