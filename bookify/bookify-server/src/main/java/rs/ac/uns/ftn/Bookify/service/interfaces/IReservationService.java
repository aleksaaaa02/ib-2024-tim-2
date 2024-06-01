package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {

    public Reservation get(Long reservationId);
    public boolean hasFutureReservationsGuest(String userId);
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation);
    public boolean hasReservationInRange(Long accommodationId, LocalDate start, LocalDate end);
    Reservation save(Reservation reservation);
    public void setAccommodation(Accommodation accommodation, Reservation reservation);
    public void setGuest(Guest guest, Reservation reservation);
    public List<Reservation> getReservations(String guestId, String ownerId);
    public List<Reservation> getReservationsForAccommodationInLast7Days(String guestId, Long accommodationId);
    public List<Reservation> getAllForGuest(String userId);
    public List<Object[]> getGuestAccommodations(String UserId);
    public List<Reservation> filterForGuest(String userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);
    List<Reservation> getAllForOwner(String userId);
    public List<Object[]> getOwnerAccommodations(String UserId);
    public List<Reservation> filterForOwner(String userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);
    public void setReservationStatus(Long reservationId, Status status);
    public void delete(Long reservationId);
    public void rejectOverlappingReservations(Long accommodationId, LocalDate startDate, LocalDate endDate);
    public Reservation accept(Long reservationId);
    public Reservation reject(Long reservationId);
    public boolean cancelGuestsReservations(String guestId);
    public Reservation cancelReservation(Long reservationId);
    public List<Reservation> getAllGuestReservations(String guestId);
}
