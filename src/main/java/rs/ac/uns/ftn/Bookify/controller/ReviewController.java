package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDetailDTO;
import rs.ac.uns.ftn.Bookify.dto.ReviewDTO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
//    @Autowired
//    private IReviewService reviewService;

    @GetMapping(value = "/reviews/created", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getAllCreatedReviews(){
        //returns all created reviews
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", LocalDate.now(), false, false, 1L);
        ReviewDTO review2 = new ReviewDTO(2L, 3, "Bad", LocalDate.now(), false, false, 1L);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/reviews/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getAllReportedReviews(){
        //returns all reported reviews
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", LocalDate.now(), false, true, 1L);
        ReviewDTO review2 = new ReviewDTO(2L, 3, "Bad", LocalDate.now(), false, true, 1L);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation-reviews/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getReviewsForAccommodation(@PathVariable Long accommodationId){
        //returns reviews for accommodation
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", LocalDate.now(), true, false, 2L);
        ReviewDTO review2 = new ReviewDTO(1L, 3, "Bad", LocalDate.now(), true, false, 1L);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/owner-reviews/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getReviewsForOwner(@PathVariable Long ownerId){
        //returns reviews for owner
        ReviewDTO review1 = new ReviewDTO(3L, 4, "Nice", LocalDate.now(), true, false, 2L);
        ReviewDTO review2 = new ReviewDTO(4L, 3, "Bad", LocalDate.now(), true, false, 1L);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/new-review-accommodation/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> newReviewAccommodation(@PathVariable Long accommodationId, @RequestBody ReviewDTO review) {
        //insert new review for accommodation
        ReviewDTO savedReview = new ReviewDTO(1L, 4, "Nice", LocalDate.now(), true, false, 2L);
        return new ResponseEntity<ReviewDTO>(savedReview, HttpStatus.CREATED);
    }

    @PostMapping(value = "/new-review-owner/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> newReviewOwner(@PathVariable Long ownerId, @RequestBody ReviewDTO review) {
        //insert new review for owner
        ReviewDTO savedReview = new ReviewDTO(1L, 4, "Nice", LocalDate.now(), true, false, 2L);
        return new ResponseEntity<ReviewDTO>(savedReview, HttpStatus.CREATED);
    }

    @PutMapping(value="/review/reject/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> rejectReview(@PathVariable Long reviewId) {
        //change to rejected
        ReviewDTO rejectReview = new ReviewDTO(1L, 4, "Nice", LocalDate.now(), false, true, 2L);
        return new ResponseEntity<ReviewDTO>(rejectReview, HttpStatus.OK);
    }

    @PutMapping(value="/review/accept/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> acceptReview(@PathVariable Long reviewId) {
        //change to accepted
        ReviewDTO acceptReview = new ReviewDTO(1L, 4, "Nice", LocalDate.now(), true, false, 2L);
        return new ResponseEntity<ReviewDTO>(acceptReview, HttpStatus.OK);
    }

    @DeleteMapping("/accommodation-review-delete/{accommodationId}/{reviewId}")
    public ResponseEntity<ReviewDTO> deleteAccommodationReview(@PathVariable Long reviewId, @PathVariable Long accommodationId) {
        //delete review for accommodation
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/owner-review-delete/{ownerId}/{reviewId}")
    public ResponseEntity<ReviewDTO> deleteOwnerReview(@PathVariable Long reviewId, @PathVariable Long ownerId) {
        //delete review for owner
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }
}
