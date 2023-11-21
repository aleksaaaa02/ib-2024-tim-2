package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDetailDTO;
import rs.ac.uns.ftn.Bookify.dto.ReviewDTO;

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
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/reviews/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getAllReportedReviews(){
        //returns all reported reviews
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getReviewsForAccommodation(@PathVariable Long accommodationId){
        //returns reviews for accommodation
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getReviewsForOwner(@PathVariable Long ownerId){
        //returns reviews for owner
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> newReviewAccommodation(@PathVariable Long accommodationId, @RequestBody ReviewDTO review) {
        //insert new review for accommodation
        ReviewDTO savedReview = new ReviewDTO();
        return new ResponseEntity<ReviewDTO>(savedReview, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> newReviewOwner(@PathVariable Long ownerId, @RequestBody ReviewDTO review) {
        //insert new review for owner
        ReviewDTO savedReview = new ReviewDTO();
        return new ResponseEntity<ReviewDTO>(savedReview, HttpStatus.CREATED);
    }

    @PutMapping(value="/reject/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> rejectReview(@PathVariable Long reviewId) {
        //change to rejected
        ReviewDTO rejectReview = new ReviewDTO();
        return new ResponseEntity<ReviewDTO>(rejectReview, HttpStatus.OK);
    }

    @PutMapping(value="/accept/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> acceptReview(@PathVariable Long reviewId) {
        //change to accepted
        ReviewDTO acceptReview = new ReviewDTO();
        return new ResponseEntity<ReviewDTO>(acceptReview, HttpStatus.OK);
    }

    @DeleteMapping("/{accommodationId}/{reviewId}")
    public ResponseEntity<ReviewDTO> deleteAccommodationReview(@PathVariable Long reviewId, @PathVariable Long accommodationId) {
        //delete review for accommodation
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{ownerId}/{reviewId}")
    public ResponseEntity<ReviewDTO> deleteOwnerReview(@PathVariable Long reviewId, @PathVariable Long ownerId) {
        //delete review for owner
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }
}
