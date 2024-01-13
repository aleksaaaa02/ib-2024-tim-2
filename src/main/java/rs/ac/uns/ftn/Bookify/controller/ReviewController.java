package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.CommentDTO;
import rs.ac.uns.ftn.Bookify.dto.NewReviewDTO;
import rs.ac.uns.ftn.Bookify.dto.RatingDTO;
import rs.ac.uns.ftn.Bookify.dto.ReviewDTO;
import rs.ac.uns.ftn.Bookify.enumerations.ReviewType;
import rs.ac.uns.ftn.Bookify.mapper.NewReviewDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.Review;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReviewService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.util.Date;
import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(value = "/created", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<ReviewDTO>> getAllCreatedReviews() {
        //returns all created reviews
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", new Date(), false, false, 1L, ReviewType.ACCOMMODATION);
        ReviewDTO review2 = new ReviewDTO(2L, 3, "Bad", new Date(), false, false, 1L, ReviewType.ACCOMMODATION);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<ReviewDTO>> getAllReportedReviews() {
        //returns all reported reviews
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", new Date(), false, true, 1L, ReviewType.ACCOMMODATION);
        ReviewDTO review2 = new ReviewDTO(2L, 3, "Bad", new Date(), false, true, 1L, ReviewType.ACCOMMODATION);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDTO>> getReviewsForAccommodation(@PathVariable Long accommodationId) {
        //returns reviews for accommodation
        Collection<CommentDTO> dtos = reviewService.getAccommodationComments(accommodationId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDTO>> getReviewsForOwner(@PathVariable Long ownerId) {
        //returns reviews for owner
        Collection<CommentDTO> dtos = reviewService.getOwnerComments(ownerId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{ownerId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> getOwnerRating(@PathVariable Long ownerId) {
        RatingDTO dto = reviewService.getOwnerRating(ownerId);
        return new ResponseEntity<RatingDTO>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> getAccommodationRating(@PathVariable Long accommodationId) {
        RatingDTO dto = reviewService.getAccommodationRating(accommodationId);
        return new ResponseEntity<RatingDTO>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/new-accommodation/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<NewReviewDTO> newReviewAccommodation(@PathVariable Long accommodationId, @RequestBody NewReviewDTO newReview) {
        //insert new review for accommodation

        Review review = NewReviewDTOMapper.fromDTOtoReview(newReview);
        Guest guest = userService.getGuest(newReview.getGuestId());
        review.setGuest(guest);
        review.setReviewType(ReviewType.ACCOMMODATION);
        if (reservationService.getReservationsForAccommodationInLast7Days(guest.getId(), accommodationId).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Review insertedReview = reviewService.save(review);
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        accommodation.getReviews().add(insertedReview);
        accommodationService.saveAccommodation(accommodation);

        return new ResponseEntity<>(newReview, HttpStatus.OK);
    }

    @PostMapping(value = "/new-owner/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<NewReviewDTO> newReviewOwner(@PathVariable Long ownerId, @RequestBody NewReviewDTO newReview) {
        //insert new review for owner

        Review review = NewReviewDTOMapper.fromDTOtoReview(newReview);
        Guest guest = userService.getGuest(newReview.getGuestId());
        review.setGuest(guest);
        review.setReviewType(ReviewType.OWNER);
        if (reservationService.getReservations(guest.getId(), ownerId).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Review insertedReview = reviewService.save(review);
        Owner owner = userService.getOwner(ownerId);
        owner.getReviews().add(insertedReview);
        userService.saveOwner(owner);

        return new ResponseEntity<>(newReview, HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ReviewDTO> rejectReview(@PathVariable Long reviewId) {
        //change to rejected
        ReviewDTO rejectReview = new ReviewDTO(1L, 4, "Nice", new Date(), false, true, 2L, ReviewType.ACCOMMODATION);
        return new ResponseEntity<ReviewDTO>(rejectReview, HttpStatus.OK);
    }

    @PutMapping(value = "/accept/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ReviewDTO> acceptReview(@PathVariable Long reviewId) {
        //change to accepted
        ReviewDTO acceptReview = new ReviewDTO(1L, 4, "Nice", new Date(), true, false, 2L, ReviewType.ACCOMMODATION);
        return new ResponseEntity<ReviewDTO>(acceptReview, HttpStatus.OK);
    }

    @PutMapping(value = "/report/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Long> reportReview(@PathVariable Long reviewId) {
        //change to report
        reviewService.reportReview(reviewId);
        return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
    }

    @DeleteMapping("/accommodation-delete/{accommodationId}/{reviewId}")
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<ReviewDTO> deleteAccommodationReview(@PathVariable Long reviewId, @PathVariable Long accommodationId) {
        //delete review for accommodation
        Review review = reviewService.getReview(reviewId);
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        accommodation.getReviews().remove(review);
//        accommodation.setDeleted(false);
        accommodationService.saveAccommodation(accommodation);
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/owner-delete/{ownerId}/{reviewId}")
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public ResponseEntity<ReviewDTO> deleteOwnerReview(@PathVariable Long reviewId, @PathVariable Long ownerId) {
        //delete review for owner
        Review review = reviewService.getReview(reviewId);
        Owner owner = userService.getOwner(ownerId);
        owner.getReviews().remove(review);
//        owner.setDeleted(false);
        userService.saveOwner(owner);
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }
}
