package rs.ac.uns.ftn.Bookify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	private Long id;
	private int rate;
	private String comment;
	private Date date;
	private boolean accepted;
	private boolean reported;
	private Long guestId;
}
