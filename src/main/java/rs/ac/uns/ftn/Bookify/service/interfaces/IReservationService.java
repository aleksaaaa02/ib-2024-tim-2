package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.util.Collection;

public interface IReservationService {

    public Reservation get(Long reservationId);
    public boolean hasFutureReservationsGuest(Long userId);
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation);

    Reservation save(Reservation reservation);
    public void setAccommodation(Accommodation accommodation, Reservation reservation);
    public void setGuest(Guest guest, Reservation reservation);
}
