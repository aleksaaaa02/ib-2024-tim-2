package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.ChartDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
//    @Autowired
//    private IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> getReservations() {
        //return all reservations
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserId(@PathVariable Long userId) {
        //return all reservations of one user
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserIdAndStatus(@PathVariable Long userId, @PathVariable Status status) {
        // return all reservations of one user where reservation status == status (g, tabs)
        Collection<ReservationDTO> reservations = new HashSet<>();
        reservations.add(new ReservationDTO(1L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 2, new Guest(), new Accommodation(), Status.PENDING));
        reservations.add(new ReservationDTO(2L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 1, new Guest(), new Accommodation(), Status.PENDING));
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> insert(@RequestBody ReservationDTO reservation) {
        //insert new reservation request (g)
        ReservationDTO savedReservation = new ReservationDTO(1L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 2, new Guest(), new Accommodation(), Status.PENDING);
        return new ResponseEntity<ReservationDTO>(savedReservation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/cancel/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        //change status into canceled
        ReservationDTO canceledReservation = new ReservationDTO(1L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 2, new Guest(), new Accommodation(), Status.PENDING);
        return new ResponseEntity<ReservationDTO>(canceledReservation, HttpStatus.OK);
    }

    @PutMapping(value = "/accept/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> acceptReservation(@PathVariable Long reservationId) {
        //change status into accepted
        ReservationDTO acceptedReservation = new ReservationDTO(1L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 2, new Guest(), new Accommodation(), Status.PENDING);
        return new ResponseEntity<ReservationDTO>(acceptedReservation, HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable Long reservationId) {
        //change status into rejected
        ReservationDTO rejectedReservation = new ReservationDTO(1L, LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 11),
                LocalDate.of(2023, 11, 12), 2, new Guest(), new Accommodation(), Status.PENDING);
        return new ResponseEntity<ReservationDTO>(rejectedReservation, HttpStatus.OK);
    }

    //???
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> delete(@PathVariable Long reservationId) {
        //delete reservation???
        return new ResponseEntity<ReservationDTO>(HttpStatus.NO_CONTENT);
    }


    //charts

    @GetMapping(value = "/charts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ChartDTO>> getChartsByPeriod(@RequestParam("ownerId") Long ownerId, @RequestParam("begin")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        //return all charts for period
        Collection<ChartDTO> charts = new HashSet<>();
        charts.add(new ChartDTO(12, 32.2));
        charts.add(new ChartDTO(1, 2.1));
        charts.add(new ChartDTO(22, 75.8));
        return new ResponseEntity<Collection<ChartDTO>>(charts, HttpStatus.OK);
    }

    @GetMapping(value = "/charts/{ownerId}/{accommodationId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ChartDTO>> getChartsByAccommodation(@PathVariable Long ownerId, @PathVariable Long accommodationId, @PathVariable int year) {
        //return all charts for accommodation
        Collection<ChartDTO> charts = new HashSet<>();
        charts.add(new ChartDTO(12, 32.2));
        charts.add(new ChartDTO(1, 2.1));
        charts.add(new ChartDTO(22, 75.8));
        return new ResponseEntity<Collection<ChartDTO>>(charts, HttpStatus.OK);
    }

    //download button??
}
