package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
//    @Autowired
//    private IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> getReservations() {
        //return all reservations
        Collection<ReservationDTO> reservations = new HashSet<>();
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserId(@PathVariable Long userId) {
        //return all reservations of one user
        Collection<ReservationDTO> reservations = new HashSet<>();
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserIdAndStatus(@PathVariable Long userId, @PathVariable Status status) {
        // return all reservations of one user where reservation status == status (g, tabs)
        Collection<ReservationDTO> reservations = new HashSet<>();
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> insert(@RequestBody ReservationDTO reservation) {
        //insert new reservation request (g)
        ReservationDTO savedReservation = new ReservationDTO();
        return new ResponseEntity<ReservationDTO>(savedReservation, HttpStatus.CREATED);
    }

    @PutMapping(value="/cancel/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        //change status into canceled
        ReservationDTO canceledReservation = new ReservationDTO();
        return new ResponseEntity<ReservationDTO>(canceledReservation, HttpStatus.OK);
    }

    @PutMapping(value="/accept/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> acceptReservation(@PathVariable Long reservationId) {
        //change status into accepted
        ReservationDTO acceptedReservation = new ReservationDTO();
        return new ResponseEntity<ReservationDTO>(acceptedReservation, HttpStatus.OK);
    }

    @PutMapping(value="/reject/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable Long reservationId) {
        //change status into rejected
        ReservationDTO rejectedReservation = new ReservationDTO();
        return new ResponseEntity<ReservationDTO>(rejectedReservation, HttpStatus.OK);
    }

    //???
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> delete(@PathVariable Long reservationId) {
        //delete reservation???
        return new ResponseEntity<ReservationDTO>(HttpStatus.NO_CONTENT);
    }
}
