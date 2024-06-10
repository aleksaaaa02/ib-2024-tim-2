package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Inheritance(strategy = InheritanceType.JOINED)
public class Address {
	@Column(length = 50)
	@Pattern(regexp = "^[a-zA-Z0-9!?,.\\-\\s]*$")
	private String country;

	@Column(length = 30)
	@Pattern(regexp = "^[a-zA-Z0-9!?,.\\-\\s]*$")
	private String city;

	@Column(length = 52)
	@Pattern(regexp = "^[a-zA-Z0-9!?,.\\-\\s]*$")
	private String address;

	@Column(length = 10)
	@Pattern(regexp = "^[a-zA-Z0-9!?,.\\-\\s]*$")
	private String zipCode;
}
