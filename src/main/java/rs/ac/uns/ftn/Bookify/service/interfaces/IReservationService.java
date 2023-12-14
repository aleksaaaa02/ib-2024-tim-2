package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.util.Collection;

public interface IReservationService {

    public Reservation get(Long reservationId);
    public boolean hasFutureReservationsGuest(Long userId);
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation);

    Reservation save(Reservation reservation);
    public void setAccommodation(Long accommodationId, Reservation reservation);
    public void setGuest(Long guestId, Reservation reservation);
}
