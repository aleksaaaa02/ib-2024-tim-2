package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.model.User;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@Service
public class ReservationService implements IReservationService {

    IReservationRepository reservationRepository;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation get(Long reservationId) {
        return null;
    }

    @Override
    public boolean hasFutureReservationsGuest(Long guestId) {
        List<Reservation> futureReservations = this.reservationRepository.findByGuest_IdAndEndAfterAndStatusNotIn(guestId, LocalDate.now(), EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !futureReservations.isEmpty();
    }

    @Override
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation) {
        List<Reservation> futureReservations = this.reservationRepository.findByAccommodation_IdAndEndAfterAndStatusNotIn(accommodation.getId(), LocalDate.now(), EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !futureReservations.isEmpty();
    }

    @Override
    public boolean hasReservationInRange(Long accommodationId, LocalDate start, LocalDate end) {
        List<Reservation> reservations = reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(accommodationId, end, start, EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !reservations.isEmpty();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void setAccommodation(Accommodation accommodation, Reservation reservation) {
        reservation.setAccommodation(accommodation);
        reservationRepository.save(reservation);
    }

    @Override
    public void setGuest(Guest guest, Reservation reservation) {
        reservation.setGuest(guest);
        reservationRepository.save(reservation);
    }
}
