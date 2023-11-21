package rs.ac.uns.ftn.Bookify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

	private Long id;
	private double rate;
	private String comment;
	private LocalDate date;
	private boolean accepted;
	private boolean reported;
	private Guest guest;

}
