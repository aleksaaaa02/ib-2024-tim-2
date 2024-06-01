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
import java.util.Map;
import java.util.Set;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findByGuest_UidAndEndAfterAndStatusNotIn(String guest, LocalDate today, Set<Status> statuses);
    public List<Reservation> findByAccommodation_IdAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate today, Set<Status> statuses);
    @Query("SELECT r FROM Reservation r WHERE r.accommodation.id = :accommodationId " +
            "AND r.start <= :end " +
            "AND r.end >= :start " +
            "AND r.status NOT IN :statuses")
    public List<Reservation> findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate end, LocalDate start, Set<Status> statuses);

    @Query("SELECT r FROM Reservation r WHERE r.guest.uid = :guestId " +
            "AND r.end <= :date " +
            "AND r.status = :status " +
            "AND r.accommodation IN (SELECT o.accommodations FROM Owner o WHERE o.uid = :ownerId)")
    public List<Reservation> getReservations(String guestId, LocalDate date, Status status, String ownerId);

    @Query("SELECT r FROM Reservation r WHERE r.guest.uid = :guestId " +
            "AND r.end >= :startDate " +
            "AND r.end <= :endDate " +
            "AND r.status = :status " +
            "AND r.accommodation.id = :accommodationId")
    public List<Reservation> getReservationsForAccommodationInLast7Days(String guestId, LocalDate startDate, LocalDate endDate, Status status, Long accommodationId);

    @Query("SELECT r FROM Reservation r WHERE r.guest.uid = :guestId")
    public List<Reservation> getAllForGuest(String guestId);

    @Query("SELECT DISTINCT a.id as id, a.name as name FROM Reservation r JOIN r.accommodation a WHERE r.guest.uid = :userId")
    List<Object[]> getIdToNameGuestMap(String userId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.guest.uid = :userId " +
            "AND r.accommodation.id = :accommodationId " +
            "AND r.start >= :startDate " +
            "AND r.end <= :endDate AND " +
            "r.status IN :statuses")
    List<Reservation> filterForGuest(String userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);

    @Query("SELECT r " +
            "FROM Owner o " +
            "JOIN o.accommodations a "+
            "JOIN Reservation r ON r.accommodation.id = a.id " +
            "WHERE o.uid = :ownerId")
    public List<Reservation> getAllForOwner(String ownerId);


    @Query("SELECT DISTINCT a.id as id, a.name as name " +
            "FROM Owner o " +
            "JOIN o.accommodations a " +
            "JOIN Reservation r ON r.accommodation.id = a.id " +
            "WHERE o.uid = :userId")
    List<Object[]> getIdToNameOwnerMap(String userId);

    @Query("SELECT r " +
            "FROM Owner o " +
            "JOIN o.accommodations a "+
            "JOIN Reservation r ON r.accommodation.id = a.id " +
            "WHERE o.uid = :userId " +
            "AND r.accommodation.id = :accommodationId " +
            "AND r.start >= :startDate " +
            "AND r.end <= :endDate AND " +
            "r.status IN :statuses")
    List<Reservation> filterForOwner(String userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);

    @Query("SELECT count(r) FROM Reservation r WHERE r.guest.uid = :userId AND r.status = 'CANCELED'")
    int getCancellationTimes(String userId);

    List<Reservation> findAllByGuest_UidAndEndAfter(String guestId, LocalDate date);
}
