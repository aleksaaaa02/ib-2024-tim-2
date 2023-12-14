package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@Service
public class ReservationService implements IReservationService {

    private final IReservationRepository reservationRepository;

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
        List<Reservation> futureReservations = this.reservationRepository.findByGuest_IdAndEndAfterAndStatusNotIn(guestId, new Date(), EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !futureReservations.isEmpty();
    }

    @Override
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation) {
        List<Reservation> futureReservations = this.reservationRepository.findByAccommodation_IdAndEndAfterAndStatusNotIn(accommodation.getId(), new Date(), EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !futureReservations.isEmpty();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
