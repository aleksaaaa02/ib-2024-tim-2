package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.CommentDTO;
import rs.ac.uns.ftn.Bookify.dto.RatingDTO;
import rs.ac.uns.ftn.Bookify.model.Review;

import java.util.Collection;
import java.util.List;

public interface IReviewService {
    public RatingDTO getRating(Long ownerId);
    public Collection<CommentDTO> getOwnerComments(Long ownerId);
    public Review save(Review review);
    public Review getReview(Long id);
    public void deleteReview(Long id);
    public void reportReview(Long id);
    public List<Review> getCreatedReviews();
    public List<Review> getReportedReviews();
}
