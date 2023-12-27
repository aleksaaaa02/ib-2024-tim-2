package rs.ac.uns.ftn.Bookify.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.mapper.*;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IImageService imageService;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchResponseDTO> getAccommodationBasics(@RequestParam("location") String location, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end, @RequestParam("persons")
    int persons, @RequestParam("page") int page, @RequestParam("size") int size) {
        //return all basic info of accommodations for search
        LocalDate beginL = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Collection<Accommodation> accommodations = accommodationService.getAccommodationsForSearch(persons, location, beginL, endL);
        Collection<AccommodationBasicDTO> accommodationBasicDTO = accommodations.stream()
                .map(AccommodationBasicDTOMapper::fromAccommodationToBasicDTO)
                .collect(Collectors.toList());

        SearchResponseDTO searchResponseDTO = accommodationService.getSearchResponseForSearch(accommodationBasicDTO, beginL, endL, persons, location, page, size);
        return new ResponseEntity<>(searchResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchResponseDTO> getAccommodationBasicsByFilter(@RequestParam("location") String location, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end, @RequestParam("persons")
    int persons, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestBody FilterDTO filter) {
        //return all basic info of accommodations for search
        LocalDate beginL = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Collection<Accommodation> accommodations = accommodationService.filterAccommodations(persons, location, beginL, endL, filter);
        List<AccommodationBasicDTO> accommodationBasicDTO = accommodations.stream()
                    .map(AccommodationBasicDTOMapper::fromAccommodationToBasicDTO)
                    .collect(Collectors.toList());

        SearchResponseDTO searchResponseDTO = accommodationService.getSearchReposnseForFilter(accommodationBasicDTO, beginL, endL, persons, location, page, size, sort, filter);
        return new ResponseEntity<>(searchResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/details/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailDTO> getAccommodationDetails(@PathVariable Long accommodationId) {
        //returns details about one accommodation
        AccommodationDetailDTO accommodationDetailDTO = accommodationService.getAccommodationDetails(accommodationId);
        return new ResponseEntity<>(accommodationDetailDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/price", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Double> getTotalPrice(@RequestParam("id") Long id, @RequestParam("begin") @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end, @RequestParam("pricePer") PricePer pricePer, @RequestParam("persons") int persons) {
        LocalDate beginL = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        double response = -1;
        if (accommodationService.isAvailable(id, beginL, endL) && accommodationService.checkPersons(id, persons))
            response = accommodationService.getTotalPrice(id, beginL, endL, pricePer, persons);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Collection<AccommodationOwnerDTO>> getOwnersAccommodations(@PathVariable Long ownerId) {
        return new ResponseEntity<>(this.accommodationService.getOwnerAccommodation(ownerId), HttpStatus.OK);
    }

    @GetMapping("/favorites")
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Collection<AccommodationBasicDTO>> getFavoritesAccommodations(@RequestParam("guestId") Long guestId) {
        //returns all favorites accommodation of user
        User user = userService.get(guestId);
        List<Accommodation> accommodations = new ArrayList<>();
        if (user instanceof Guest)
            accommodations = ((Guest) user).getFavorites();

        Collection<AccommodationBasicDTO> accommodationBasicDTO = accommodations.stream()
                .map(AccommodationBasicDTOMapper::fromAccommodationToBasicDTO)
                .collect(Collectors.toList());
        for (AccommodationBasicDTO accommodationBasic : accommodationBasicDTO)
            accommodationBasic.setAvgRating(accommodationService.getAvgRating(accommodationBasic.getId()));
        return new ResponseEntity<>(accommodationBasicDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/charts", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
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
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<String> downloadChartsByPeriod(@RequestParam("ownerId") Long ownerId, @RequestParam("begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy") Date begin, @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") Date end) {
        //download pdf report for period
        return new ResponseEntity<>("PDF", HttpStatus.OK);
    }

    @GetMapping(value = "/charts/{accommodationId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Collection<ChartDTO>> getChartsByAccommodation(@PathVariable Long accommodationId, @PathVariable int year) {
        //return all charts for accommodation
        Collection<ChartDTO> charts = new HashSet<>();
        charts.add(new ChartDTO(12, 32.2));
        charts.add(new ChartDTO(1, 2.1));
        charts.add(new ChartDTO(22, 75.8));
        return new ResponseEntity<Collection<ChartDTO>>(charts, HttpStatus.OK);
    }

    @GetMapping(value = "/charts-download/{accommodationId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<String> downloadChartsByAccommodation(@PathVariable Long accommodationId, @PathVariable int year) {
        //download pdf report for accommodation in one year
        return new ResponseEntity<>("PDF", HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Accommodation> insert(@RequestParam Long ownerId, @RequestBody AccommodationInsertDTO accommodationDTO) {
        //insert new accommodation
        Accommodation accommodation = AccommodationInesertDTOMapper.fromDTOtoAccommodation(accommodationDTO);

        Accommodation a = accommodationService.save(accommodation);
        userService.saveOwnerAccommodation(accommodation, ownerId);

        return new ResponseEntity<Accommodation>(a, HttpStatus.CREATED);
    }

    @PostMapping("/add-to-favorites/{guestId}/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<String> addAccommodationToFavorites(@PathVariable Long guestId, @PathVariable Long accommodationId) {
        //inserts accommodation to favorites
        accommodationService.insertForGuest(guestId, accommodationId);
        return new ResponseEntity<>("Accommodation added to favorites", HttpStatus.OK);
    }

    @GetMapping("/added-to-favorites/{guestId}/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<Boolean> addedAccommodationToFavorites(@PathVariable Long guestId, @PathVariable Long accommodationId) {
        //inserts accommodation to favorites
        boolean result = userService.checkIfInFavorites(guestId, accommodationId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Long> updateAccommodation(@RequestBody AccommodationDTO dto) throws Exception {
        //update accommodation
        Accommodation accommodation = AccommodationDTOMapper.fromDTOtoAccommodation(dto);
        accommodationService.update(accommodation);
        return new ResponseEntity<Long>(accommodation.getId(), HttpStatus.OK);
    }

    @PutMapping(value = "/approve/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> approveAccommodation(@PathVariable Long accommodationId) {
        //change to accepted
        this.accommodationService.setAccommodationStatus(accommodationId, AccommodationStatusRequest.APPROVED);
        return new ResponseEntity<>(String.format("Accommodation %d approved", accommodationId), HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> rejectAccommodation(@PathVariable Long accommodationId) {
        this.accommodationService.setAccommodationStatus(accommodationId, AccommodationStatusRequest.REJECTED);
        return new ResponseEntity<>(String.format("Accommodation %d rejected", accommodationId), HttpStatus.OK);
    }

    @DeleteMapping("/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<String> delete(@PathVariable Long accommodationId) {
        //delete accommodation
        return new ResponseEntity<>("Accommodation removed", HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<FileSystemResource> getAccommodationImage(@PathVariable Long imageId) {
        return new ResponseEntity<>(imageService.find(imageId), HttpStatus.OK);
    }

    @GetMapping(value = "/images/{accommodationId}")
    public ResponseEntity<Collection<byte[]>> getAccommodationImages(@PathVariable Long accommodationId) throws IOException {
        Collection<byte[]> data = new ArrayList<>();
        for(FileSystemResource f : accommodationService.getAllImages(accommodationId)) data.add(f.getContentAsByteArray());
        return new ResponseEntity<>(data,  HttpStatus.OK);
    }

    @GetMapping(value = "/images/files/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ImageMobileDTO>> getAccommodationImagesFiles(@PathVariable Long accommodationId) throws IOException {
        Collection<ImageMobileDTO> data = new ArrayList<>();
        for(FileSystemResourcesDTO f : accommodationService.getAllImagesDTO(accommodationId)) {
            data.add(new ImageMobileDTO(f.getFile().getContentAsByteArray(), f.getId()));
        }
        return new ResponseEntity<>(data,  HttpStatus.OK);
    }

    @PostMapping("/images/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Long> uploadAccommodationImage(@PathVariable Long accommodationId, @RequestParam MultipartFile image) throws Exception {
        imageService.save(image.getBytes(), accommodationId.toString(), image.getName());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Long> uploadAccommodationImages(@PathVariable Long accommodationId, @RequestParam("images") List<MultipartFile> images) throws Exception {
        imageService.save(accommodationId, images);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/{accommodationId}/addPrice")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Long> addPriceListItem(@PathVariable Long accommodationId, @RequestBody PriceListItemDTO dto) {
        PricelistItem item = PriceListItemDTOMapper.fromDTOtoPriceListItem(dto);
        Availability availability = PriceListItemDTOMapper.fromDTOtoAvailability(dto);
        accommodationService.addPriceList(accommodationId, item);
        accommodationService.addAvailability(accommodationId, availability);
        return new ResponseEntity<>(accommodationId, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/getPrice", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Collection<PriceListItemDTO>> getAccommodationPriceListItems(@PathVariable Long accommodationId) {
        Collection<PricelistItem> priceListItems = accommodationService.getAccommodationPriceListItems(accommodationId);
        Collection<PriceListItemDTO> priceListItemDTOS = PriceListItemDTOMapper.fromPriceListItemtoDTO(priceListItems);
        return new ResponseEntity<>(priceListItemDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/price/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<PriceListItemDTO> deletePriceList(@PathVariable Long accommodationId, @RequestBody PriceListItemDTO dto) {
        PricelistItem pricelistItem = PriceListItemDTOMapper.fromDTOtoPriceListItem(dto);
        accommodationService.deletePriceListItem(accommodationId, pricelistItem);
        return new ResponseEntity<PriceListItemDTO>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/edit/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<AccommodationInsertDTO> getAccommodation(@PathVariable Long accommodationId) {
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        AccommodationInsertDTO accommodationInsertDTO = AccommodationInesertDTOMapper.fromAccommodationtoDTO(accommodation);
        return new ResponseEntity<>(accommodationInsertDTO, HttpStatus.OK);
    }

    @DeleteMapping("/images/{imageId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Long> deleteAccommodationImage(@PathVariable Long imageId) throws Exception {
        imageService.deleteById(imageId);
        return new ResponseEntity<Long>(imageId, HttpStatus.OK);
    }

    @GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<AccommodationRequestDTO>> getRequests() {
        return new ResponseEntity<>(this.userService.findAccommodationRequests(), HttpStatus.OK);
    }
}
