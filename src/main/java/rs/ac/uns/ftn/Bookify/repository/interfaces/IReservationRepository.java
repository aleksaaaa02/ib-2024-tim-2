package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findByGuest_IdAndEndAfterAndStatusNotIn(Long guest, Date today, Set<Status> statuses);
    public List<Reservation> findByAccommodation_IdAndEndAfterAndStatusNotIn(Long accommodationId, Date today, Set<Status> statuses);
}
