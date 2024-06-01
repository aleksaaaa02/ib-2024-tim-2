package rs.ac.uns.ftn.Bookify.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public abstract class User implements Serializable {

	@Id
	@Column(unique = true)
	private String uid;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column
	private boolean blocked = Boolean.FALSE;

	@Column
	private String phone;

	@Column
	private boolean deleted = Boolean.FALSE;

	@OneToMany
	private Collection<Notification> notifications;

	@OneToOne
	private Image profileImage;

	@Embedded
	private Address address;

	public String getUserType() {
		DiscriminatorValue discriminatorValue = getClass().getAnnotation(DiscriminatorValue.class);
		return (discriminatorValue != null) ? discriminatorValue.value() : null;
	}
}