package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDetailDTO;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/accommodation")
public class AccommodationController {
    //    @Autowired
    //    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getAccommodationBasics(){
        //return all basic info of accommodations
        Collection<AccommodationBasicDTO> basicAccommodations = new HashSet<>();
        return new ResponseEntity<>(basicAccommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailDTO> getAccommodationDetails(@PathVariable Long accommodationId){
        //returns details about one accommodation
        AccommodationDetailDTO accommodationDetailDTO = new AccommodationDetailDTO();
        return new ResponseEntity<>(accommodationDetailDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getOwnersAccommodations(@PathVariable Long ownerId){
        //returns all accommodations for owner
        Collection<AccommodationBasicDTO> basicAccommodations = new HashSet<>();
        return new ResponseEntity<>(basicAccommodations, HttpStatus.OK);
    }

    @GetMapping("/favorites/{guestId}")
    public ResponseEntity<Collection<AccommodationBasicDTO>> getFavoritesAccommodations(@PathVariable Long guestId){
        //returns all favorites accommodation of user
        Collection<AccommodationBasicDTO> basicAccommodations = new HashSet<>();
        return new ResponseEntity<>(basicAccommodations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> insert(@RequestBody AccommodationDTO accommodation) {
        //insert new accommodation
        AccommodationDTO savedAccommodation = new AccommodationDTO();
        return new ResponseEntity<AccommodationDTO>(savedAccommodation, HttpStatus.CREATED);
    }

    @PostMapping("/add-to-favorites/{guestId}/{accommodationId}")
    public ResponseEntity<String> addAccommodationToFavorites(@PathVariable Long guestId, @PathVariable Long accommodationId){
        //inserts accommodation to favorites
        return new ResponseEntity<>("Accommodation added to favorites", HttpStatus.OK);
    }

    @PutMapping(value = "/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@PathVariable Long accommodationId) {
        //update accommodation
        AccommodationDTO accommodationDTO = new AccommodationDTO();
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.OK);
    }

    @DeleteMapping("/remove-from-favorites/{guestId}/{accommodationId}")
    public ResponseEntity<String> removeAccommodationFromFavorites(@PathVariable Long guestId, @PathVariable Long accommodationId){
        //delete accommodation from favorites
        return new ResponseEntity<>("Accommodation removed from favorites", HttpStatus.OK);
    }

    @DeleteMapping("/{accommodationId}")
    public ResponseEntity<AccommodationDTO> delete(@PathVariable Long accommodationId) {
        //delete accommodation
        return new ResponseEntity<AccommodationDTO>(HttpStatus.NO_CONTENT);
    }
}
