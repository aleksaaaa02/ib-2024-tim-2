package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.mapper.AccommodationBasicDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.ReservationDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.ReservationRequestDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.model.User;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IAccommodationService accommodationService;

    @Autowired
    private IUserService userService;

    @GetMapping(value="/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserId(@RequestParam Long userId) {
        //return all reservations of one guest
        Collection<Reservation> reservations = reservationService.getAllForGuest(userId);
        Collection<ReservationDTO> reservationDTOS = reservations.stream()
                .map(ReservationDTOMapper::toReservationDTO)
                .collect(Collectors.toList());
        for (ReservationDTO reservation : reservationDTOS){
            reservation.setUser(userService.getOwnerForReservation(reservation.getAccommodationId()));
            reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        }
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/guest/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Collection<ReservationDTO>> FilterReservationsForGuest(@RequestParam Long userId, @RequestParam Long accommodationId,
                                                                                  @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate, @RequestParam Status[] statuses) {
        //return all reservations of one guest
        LocalDate beginL = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Collection<Reservation> reservations = reservationService.filterForGuest(userId, accommodationId, beginL, endL, statuses);

        Collection<ReservationDTO> reservationDTOS = reservations.stream()
                .map(ReservationDTOMapper::toReservationDTO)
                .collect(Collectors.toList());
        for (ReservationDTO reservation : reservationDTOS){
            reservation.setUser(userService.getOwnerForReservation(reservation.getAccommodationId()));
            reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        }
        System.out.println(reservationDTOS);
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/accommodations/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<List<Object[]>> getAccommodationNames(@RequestParam Long userId) {
        //return all accommodation names and ids for guest
        List<Object[]> returns = reservationService.getGuestAccommodations(userId);
        return new ResponseEntity<>(returns, HttpStatus.OK);
    }



    @GetMapping(value = "/{userId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserIdAndStatus(@PathVariable Long userId, @PathVariable Status status) {
        // return all reservations of one user where reservation status == status (g, tabs)
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<ReservationDTO> insert(@RequestBody ReservationRequestDTO reservationRequestDTO, @RequestParam Long accommodationId, @RequestParam Long guestId) {
        //insert new reservation request
        Reservation reservation = ReservationRequestDTOMapper.fromReservationRequestDTOToReservation(reservationRequestDTO);
        Reservation ra = reservationService.save(reservation);

        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        reservationService.setAccommodation(accommodation, ra);
        Guest guest = (Guest) userService.get(guestId);
        reservationService.setGuest(guest, ra);

        return new ResponseEntity<>(new ReservationDTO(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/cancel/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        //change status into canceled
        ReservationDTO canceledReservation = new ReservationDTO();
        return new ResponseEntity<ReservationDTO>(canceledReservation, HttpStatus.OK);
    }

    @PutMapping(value = "/accept/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<ReservationDTO> acceptReservation(@PathVariable Long reservationId) {
        //change status into accepted
        ReservationDTO acceptedReservation = new ReservationDTO();
        return new ResponseEntity<ReservationDTO>(acceptedReservation, HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable Long reservationId) {
        //change status into rejected
        ReservationDTO rejectedReservation = new ReservationDTO();
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
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/filter/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Collection<ReservationDTO>> filterGuestsRequests(@RequestParam("guestId") Long guestId, @RequestParam("accommodationId") Long accommodationId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end,
                                                                           @RequestParam("statuses") Set<Status> statuses) {
        // return all requests of one user using filters (g, tabs)
        Collection<ReservationDTO> reservations = new HashSet<>();
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }
}
