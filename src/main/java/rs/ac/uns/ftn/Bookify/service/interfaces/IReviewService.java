package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.CommentDTO;
import rs.ac.uns.ftn.Bookify.dto.RatingDTO;
import rs.ac.uns.ftn.Bookify.model.Review;

import java.util.Collection;

public interface IReviewService {
    public RatingDTO getOwnerRating(Long ownerId);
    public RatingDTO getAccommodationRating(Long accommodationId);
    public Collection<CommentDTO> getOwnerComments(Long ownerId);
    public Collection<CommentDTO> getAccommodationComments(Long accommodationId);
    public Review save(Review review);
    public Review getReview(Long id);
    public void deleteReview(Long id);
    public void reportReview(Long id);
}
