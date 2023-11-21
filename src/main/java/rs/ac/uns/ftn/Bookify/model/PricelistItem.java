package rs.ac.uns.ftn.Bookify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricelistItem {

	private Long id;
	private LocalDate startDate;
	private LocalDate endDate;
	private double price;

}
