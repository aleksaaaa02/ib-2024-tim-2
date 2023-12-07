package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.mapper.AccommodationBasicDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.AccommodationInesertDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;

    @Autowired
    private IImageService imageService;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getAccommodationBasics(@RequestParam("location") String location, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end, @RequestParam("persons")
    int persons, @RequestParam("page") int page, @RequestParam("size") int size) {
        //return all basic info of accommodations for search
        long totalResults = accommodationService.countByLocationAndGuestRange(persons, location, begin ,end);
        if (totalResults > 0) {
            int resultNumber = (int) totalResults - size * page;
            if (resultNumber <= 0)
                resultNumber = (int) totalResults;
            else if (resultNumber > size)
                resultNumber = size;

            Pageable paging = PageRequest.of(page, resultNumber);
            Collection<Accommodation> accommodations = accommodationService.getAccommodationsForSearch(persons, location, begin, end, paging).getContent();

            List<AccommodationBasicDTO> accommodationBasicDTO = accommodations.stream()
                    .map(AccommodationBasicDTOMapper::fromAccommodationToBasicDTO)
                    .collect(Collectors.toList());

            accommodationBasicDTO = accommodationService.setPrices(accommodationBasicDTO, begin, end, persons);

            return new ResponseEntity<>(accommodationBasicDTO, HttpStatus.OK);
        }
        else {
            Collection<AccommodationBasicDTO> accommodationBasicDTO = new HashSet<>();
            return new ResponseEntity<>(accommodationBasicDTO, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/search-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getAccommodationBasicsCount(@RequestParam("location") String location, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end, @RequestParam("persons")
    int persons) {
        //return all basic info of accommodations for search
        long count = accommodationService.countByLocationAndGuestRange(persons, location, begin, end);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getAccommodationBasicsByFilter(@RequestParam("location") String location, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end, @RequestParam("persons")
    int persons, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestBody FilterDTO filter) {
        //return all basic info of accommodations for search

        Collection<Accommodation> accommodations = accommodationService.getAccommodationsForSearch(persons, location, begin, end);

        List<AccommodationBasicDTO> accommodationBasicDTO = accommodations.stream()
                    .map(AccommodationBasicDTOMapper::fromAccommodationToBasicDTO)
                    .collect(Collectors.toList());

        accommodationBasicDTO = accommodationService.setPrices(accommodationBasicDTO, begin, end, persons);

        accommodationBasicDTO = accommodationService.sortAccommodationBasicDTO(accommodationBasicDTO, sort);
        if ((page+1)*size > accommodationBasicDTO.size())
            accommodationBasicDTO = accommodationBasicDTO.subList(page*size, accommodationBasicDTO.size());
        else
            accommodationBasicDTO = accommodationBasicDTO.subList(page*size, (page+1)*size);

        return new ResponseEntity<>(accommodationBasicDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/details/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailDTO> getAccommodationDetails(@PathVariable Long accommodationId) {
        //returns details about one accommodation
        List<PricelistItem> pricelistItemList = new ArrayList<>();
        pricelistItemList.add(new PricelistItem());
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability());
        AccommodationDetailDTO accommodationDetailDTO = new AccommodationDetailDTO(1L, "Hotel", "Disc", pricelistItemList, availabilities, null, null, new Address(), 2L, "First", "Last", "06338472394", 4.23f, null);
        return new ResponseEntity<>(accommodationDetailDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/top-accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getTopAccommodations() {
        //returns most popular accommodations
        AccommodationBasicDTO basicDTO1 = new AccommodationBasicDTO(1L, "Hotel", new Address(), 3.45f, 0f, PricePer.ROOM, 0f, 1L, AccommodationType.APARTMENT, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "      Quisque porttitor convallis rhoncus. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet.");
        AccommodationBasicDTO basicDTO2 = new AccommodationBasicDTO(2L, "Apartment", new Address(), 4.45f, 0f, PricePer.ROOM, 0f, 1L, AccommodationType.APARTMENT, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "      Quisque porttitor convallis rhoncus. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet.");
        Collection<AccommodationBasicDTO> basicAccommodations = new HashSet<>();
        basicAccommodations.add(basicDTO1);
        basicAccommodations.add(basicDTO2);
        return new ResponseEntity<>(basicAccommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/top-locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LocationDTO>> getTopLocations() {
        //returns most popular locations
        LocationDTO location = new LocationDTO("Novi Sad", "Serbia");
        Collection<LocationDTO> locationDTO = new HashSet<>();
        locationDTO.add(location);
        return new ResponseEntity<>(locationDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationBasicDTO>> getOwnersAccommodations(@PathVariable Long ownerId) {
        //returns all accommodations for owner
        AccommodationBasicDTO basicDTO1 = new AccommodationBasicDTO(1L, "Hotel", new Address(), 3.45f, 0f, PricePer.ROOM, 0f, 1L, AccommodationType.APARTMENT, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "      Quisque porttitor convallis rhoncus. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet.");
        AccommodationBasicDTO basicDTO2 = new AccommodationBasicDTO(2L, "Apartment", new Address(), 4.45f, 0f, PricePer.ROOM, 0f, 1L, AccommodationType.APARTMENT, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "      Quisque porttitor convallis rhoncus. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet.");
        Collection<AccommodationBasicDTO> basicAccommodations = new HashSet<>();
        basicAccommodations.add(basicDTO1);
        basicAccommodations.add(basicDTO2);
        return new ResponseEntity<>(basicAccommodations, HttpStatus.OK);
    }

    @GetMapping("/favorites/{guestId}")
    public ResponseEntity<Collection<AccommodationBasicDTO>> getFavoritesAccommodations(@PathVariable Long guestId) {
        //returns all favorites accommodation of user
        AccommodationBasicDTO basicDTO1 = new AccommodationBasicDTO(1L, "Hotel", new Address(), 3.45f, 0f, PricePer.ROOM, 0f, 1L, AccommodationType.APARTMENT, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "      Quisque porttitor convallis rhoncus. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet.");
        AccommodationBasicDTO basicDTO2 = new AccommodationBasicDTO(2L, "Apartment", new Address(), 4.45f, 0f, PricePer.ROOM, 0f, 1L, AccommodationType.APARTMENT, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "      Quisque porttitor convallis rhoncus. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet. Nunc semper, justo a\n" +
                "      bibendum luctus. Lorem ipsum dolor sit amet.");
        Collection<AccommodationBasicDTO> basicAccommodations = new HashSet<>();
        basicAccommodations.add(basicDTO1);
        basicAccommodations.add(basicDTO2);
        return new ResponseEntity<>(basicAccommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/charts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationsChartDTO>> getChartsByPeriod(@RequestParam("ownerId") Long ownerId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end) {
        //return all charts for period
        Collection<AccommodationsChartDTO> charts = new HashSet<>();
        charts.add(new AccommodationsChartDTO(new Accommodation(), 12, 32.2));
        charts.add(new AccommodationsChartDTO(new Accommodation(), 1, 2.1));
        charts.add(new AccommodationsChartDTO(new Accommodation(), 22, 75.8));
        return new ResponseEntity<Collection<AccommodationsChartDTO>>(charts, HttpStatus.OK);
    }

    @GetMapping(value = "/charts-download", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> downloadChartsByPeriod(@RequestParam("ownerId") Long ownerId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end) {
        //download pdf report for period
        return new ResponseEntity<>("PDF", HttpStatus.OK);
    }

    @GetMapping(value = "/charts/{accommodationId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ChartDTO>> getChartsByAccommodation(@PathVariable Long accommodationId, @PathVariable int year) {
        //return all charts for accommodation
        Collection<ChartDTO> charts = new HashSet<>();
        charts.add(new ChartDTO(12, 32.2));
        charts.add(new ChartDTO(1, 2.1));
        charts.add(new ChartDTO(22, 75.8));
        return new ResponseEntity<Collection<ChartDTO>>(charts, HttpStatus.OK);
    }

    @GetMapping(value = "/charts-download/{accommodationId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> downloadChartsByAccommodation(@PathVariable Long accommodationId, @PathVariable int year) {
        //download pdf report for accommodation in one year
        return new ResponseEntity<>("PDF", HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> insert(@RequestBody AccommodationInsertDTO accommodationDTO) {
        //insert new accommodation
        Accommodation accommodation = AccommodationInesertDTOMapper.fromDTOtoAccommodation(accommodationDTO);

        Accommodation a = accommodationService.save(accommodation);
        return new ResponseEntity<Accommodation>(a, HttpStatus.CREATED);
    }

    @PostMapping("/add-to-favorites/{guestId}/{accommodationId}")
    public ResponseEntity<String> addAccommodationToFavorites(@PathVariable Long guestId, @PathVariable Long accommodationId) {
        //inserts accommodation to favorites
        return new ResponseEntity<>("Accommodation added to favorites", HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody AccommodationDTO accommodation) {
        //update accommodation
        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    }

    @PutMapping(value = "/approve/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> approveAccommodation(@PathVariable Long accommodationId) {
        //change to accepted
        AccommodationDTO approveAccommodation = new AccommodationDTO();
        return new ResponseEntity<AccommodationDTO>(approveAccommodation, HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> rejectAccommodation(@PathVariable Long accommodationId) {
        //change to reject
        AccommodationDTO rejectAccommodation = new AccommodationDTO();
        return new ResponseEntity<AccommodationDTO>(rejectAccommodation, HttpStatus.OK);
    }

    @DeleteMapping("/remove-from-favorites/{guestId}/{accommodationId}")
    public ResponseEntity<String> removeAccommodationFromFavorites(@PathVariable Long guestId, @PathVariable Long accommodationId) {
        //delete accommodation from favorites
        return new ResponseEntity<>("Accommodation removed from favorites", HttpStatus.OK);
    }

    @DeleteMapping("/{accommodationId}")
    public ResponseEntity<String> delete(@PathVariable Long accommodationId) {
        //delete accommodation
        return new ResponseEntity<>("Accommodation removed", HttpStatus.OK);
    }

    @GetMapping(value = "/images/{imageId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<FileSystemResource> getAccommodationImage(@PathVariable Long imageId) {
        return new ResponseEntity<>(imageService.find(imageId), HttpStatus.OK);
    }

    @PostMapping("/images/{accommodationId}")
    public ResponseEntity<Long> uploadAccommodationImage(@PathVariable Long accommodationId, @RequestParam MultipartFile image) throws Exception {
        imageService.save(image.getBytes(), accommodationId.toString(), image.getName());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/{accommodationId}")
    public ResponseEntity<Long> uploadAccommodationImages(@PathVariable Long accommodationId, @RequestParam("images") List<MultipartFile> images) throws Exception {
        imageService.save(accommodationId, images);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
