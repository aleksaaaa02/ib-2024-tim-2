package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.mapper.ReservationRequestDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;

import java.util.*;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_GUEST','ADMIN_ROLE')")
    public ResponseEntity<Collection<ReservationDTO>> getReservations() {
        //return all reservations
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, new Date(), new Date(),
                new Date(), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserId(@PathVariable Long userId) {
        //return all reservations of one user
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, new Date(), new Date(),
                new Date(), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserIdAndStatus(@PathVariable Long userId, @PathVariable Status status) {
        // return all reservations of one user where reservation status == status (g, tabs)
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, new Date(), new Date(),
                new Date(), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Reservation> insert(@RequestBody ReservationRequestDTO reservationRequestDTO, @RequestParam Long accommodationId, @RequestParam Long guestId) {
        //insert new reservation request
        Reservation reservation = ReservationRequestDTOMapper.fromReservationRequestDTOToReservation(reservationRequestDTO);
        Reservation ra = reservationService.save(reservation);

        reservationService.setAccommodation(accommodationId, ra);
        reservationService.setGuest(guestId, ra);

        return new ResponseEntity<>(ra, HttpStatus.CREATED);
    }

    @PutMapping(value = "/cancel/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        //change status into canceled
        ReservationDTO canceledReservation = new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING);
        return new ResponseEntity<ReservationDTO>(canceledReservation, HttpStatus.OK);
    }

    @PutMapping(value = "/accept/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<ReservationDTO> acceptReservation(@PathVariable Long reservationId) {
        //change status into accepted
        ReservationDTO acceptedReservation = new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING);
        return new ResponseEntity<ReservationDTO>(acceptedReservation, HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable Long reservationId) {
        //change status into rejected
        ReservationDTO rejectedReservation = new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING);
        return new ResponseEntity<ReservationDTO>(rejectedReservation, HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> delete(@PathVariable Long reservationId) {
        //delete reservation
        return new ResponseEntity<ReservationDTO>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/filter/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Collection<ReservationDTO>> filterOwnersRequests(@RequestParam("accommodationId") Long accommodationId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end,
                                                                           @RequestParam("statuses") Set<Status> statuses) {
        // return all requests of one user using filters (g, tabs)
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, new Date(), new Date(),
                new Date(), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/filter/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Collection<ReservationDTO>> filterGuestsRequests(@RequestParam("guestId") Long guestId, @RequestParam("accommodationId") Long accommodationId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end,
                                                                           @RequestParam("statuses") Set<Status> statuses) {
        // return all requests of one user using filters (g, tabs)
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, new Date(), new Date(),
                new Date(), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, new Date(), new Date(),
                new Date(), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }
}
