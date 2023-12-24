package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findByGuest_IdAndEndAfterAndStatusNotIn(Long guest, LocalDate today, Set<Status> statuses);
    public List<Reservation> findByAccommodation_IdAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate today, Set<Status> statuses);
    @Query("SELECT r FROM Reservation r WHERE r.accommodation.id = :accommodationId " +
            "AND r.start <= :end " +
            "AND r.end >= :start " +
            "AND r.status NOT IN :statuses")
    public List<Reservation> findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate end, LocalDate start, Set<Status> statuses);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId")
    public List<Reservation> getAllForGuest(Long guestId);
}
