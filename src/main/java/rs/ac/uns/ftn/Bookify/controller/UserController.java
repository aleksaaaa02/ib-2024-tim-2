package rs.ac.uns.ftn.Bookify.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.ReportedUserDTO;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @PostMapping(value = "/report", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportedUserDTO> insertReport(@RequestBody ReportedUserDTO reservation) {
        //insert new report
        ReportedUserDTO reportedUserDTO = new ReportedUserDTO("Reason", LocalDateTime.of(2000,10,10,10,10,10), new Owner(), new Guest());
        return new ResponseEntity<ReportedUserDTO>(reportedUserDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/block/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportedUserDTO> blockUser(@PathVariable Long userId) {
        //change user status into blocked
        ReportedUserDTO reportedUserDTO = new ReportedUserDTO("Reason", LocalDateTime.of(2000,10,10,10,10,10), new Owner(), new Guest());
        return new ResponseEntity<ReportedUserDTO>(reportedUserDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/unblock/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportedUserDTO> unblockUser(@PathVariable Long userId) {
        //change user status
        ReportedUserDTO reportedUserDTO = new ReportedUserDTO("Reason", LocalDateTime.of(2000,10,10,10,10,10), new Owner(), new Guest());
        return new ResponseEntity<ReportedUserDTO>(reportedUserDTO, HttpStatus.OK);
    }
}

