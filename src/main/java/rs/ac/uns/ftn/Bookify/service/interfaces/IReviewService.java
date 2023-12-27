package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.RatingDTO;

public interface IReviewService {
    public RatingDTO getRating(Long ownerId);
}
