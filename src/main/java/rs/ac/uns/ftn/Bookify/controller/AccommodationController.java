package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/accommodation")
public class AccommodationController {
    //    @Autowired
    //    private IAccommodationService accommodationService;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getAccommodationBasics(@RequestParam("location") String location, @RequestParam("begin")
        @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate begin, @RequestParam("end")
        @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end, @RequestParam("persons") int persons){
        //return all basic info of accommodations for search
        Collection<AccommodationBasicDTO> basicAccommodations = new HashSet<>();
        return new ResponseEntity<>(basicAccommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailDTO> getAccommodationDetails(@PathVariable Long accommodationId){
        //returns details about one accommodation
        AccommodationDetailDTO accommodationDetailDTO = new AccommodationDetailDTO();
        return new ResponseEntity<>(accommodationDetailDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/top-accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getTopAccommodations(){
        //returns most popular accommodations
        Collection<AccommodationBasicDTO> accommodationBasicDTO = new HashSet<>();
        return new ResponseEntity<>(accommodationBasicDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/top-locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LocationDTO>> getTopLocations(){
        //returns most popular locations
        Collection<LocationDTO> locationDTO = new HashSet<>();
        return new ResponseEntity<>(locationDTO, HttpStatus.OK);
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

    @GetMapping(value = "/charts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ChartDTO>> getChartsByPeriod(@RequestParam("ownerId") Long ownerId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end) {
        //return all charts for period
        Collection<ChartDTO> charts = new HashSet<>();
        charts.add(new ChartDTO(12, 32.2));
        charts.add(new ChartDTO(1, 2.1));
        charts.add(new ChartDTO(22, 75.8));
        return new ResponseEntity<Collection<ChartDTO>>(charts, HttpStatus.OK);
    }

    @GetMapping(value = "/charts-download", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> downloadChartsByPeriod(@RequestParam("ownerId") Long ownerId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end) {
        //download pdf report for period
        return new ResponseEntity<>("PDF", HttpStatus.OK);
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

    @GetMapping(value = "/charts-download/{ownerId}/{accommodationId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> downloadChartsByAccommodation(@PathVariable Long ownerId, @PathVariable Long accommodationId, @PathVariable int year) {
        //download pdf report for accommodation in one year
        return new ResponseEntity<>("PDF", HttpStatus.OK);
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
    public ResponseEntity<String> delete(@PathVariable Long accommodationId) {
        //delete accommodation
        return new ResponseEntity<>("Accommodation removed", HttpStatus.OK);
    }
}
