package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IReservationService {

    public Reservation get(Long reservationId);
    public boolean hasFutureReservationsGuest(Long userId);
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation);
    public boolean hasReservationInRange(Long accommodationId, LocalDate start, LocalDate end);
    Reservation save(Reservation reservation);
    public void setAccommodation(Accommodation accommodation, Reservation reservation);
    public void setGuest(Guest guest, Reservation reservation);
    public List<Reservation> getAllForGuest(Long userId);
    public List<Object[]> getGuestAccommodations(Long UserId);
    public List<Reservation> filterForGuest(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);
}
