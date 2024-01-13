package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
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
import java.util.*;

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

    @Override
    public List<Reservation> getReservations(Long guestId, Long ownerId) {
        return reservationRepository.getReservations(guestId, LocalDate.now(), Status.ACCEPTED, ownerId);
    }

    @Override
    public List<Reservation> getReservationsForAccommodationInLast7Days(Long guestId, Long accommodationId) {
        return reservationRepository.getReservationsForAccommodationInLast7Days(guestId, LocalDate.now().minusDays(7), LocalDate.now(), Status.ACCEPTED, accommodationId);
    }

    @Override
    public List<Reservation> getAllForGuest(Long userId) {
        return reservationRepository.getAllForGuest(userId);
    }

    @Override
    public List<Object[]> getGuestAccommodations(Long userId) {
        return reservationRepository.getIdToNameGuestMap(userId);
    }

    @Override
    public List<Reservation> filterForGuest(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses) {
        return reservationRepository.filterForGuest(userId, accommodationId, startDate, endDate, statuses);
    }

    @Override
    public List<Reservation> getAllForOwner(Long userId) {
        return reservationRepository.getAllForOwner(userId);
    }

    @Override
    public List<Object[]> getOwnerAccommodations(Long userId) {
        return reservationRepository.getIdToNameOwnerMap(userId);
    }

    @Override
    public List<Reservation> filterForOwner(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses) {
        return reservationRepository.filterForOwner(userId, accommodationId, startDate, endDate, statuses);
    }

    @Override
    public void setReservationStatus(Long reservationId, Status status) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isPresent()) {
            Reservation r = reservation.get();
            r.setStatus(status);
            reservationRepository.save(r);
        }
    }

    @Override
    public void delete(Long reservationId) {
        this.reservationRepository.deleteById(reservationId);
    }
}
