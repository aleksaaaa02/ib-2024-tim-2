package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.CommentDTO;
import rs.ac.uns.ftn.Bookify.dto.RatingDTO;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Review;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReviewRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReviewService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReviewService implements IReviewService {
    private final IReviewRepository reviewRepository;

    @Autowired
    public ReviewService(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public RatingDTO getRating(Long ownerId) {
        List<Review> reviews = reviewRepository.findByOwnerId(ownerId);
        RatingDTO dto = new RatingDTO();
        for (Review review : reviews) {
            switch (review.getRate()) {
                case 1:
                    dto.setOneStars(dto.getOneStars() + 1);
                    break;
                case 2:
                    dto.setTwoStars(dto.getTwoStars() + 1);
                    break;
                case 3:
                    dto.setThreeStars(dto.getThreeStars() + 1);
                    break;
                case 4:
                    dto.setFourStars(dto.getFourStars() + 1);
                    break;
                case 5:
                    dto.setFiveStars(dto.getFiveStars() + 1);
                    break;
            }
        }
        int sum = 5 * dto.getFiveStars() + 4 * dto.getFourStars() + 3 * dto.getThreeStars() + 2 * dto.getTwoStars() + dto.getOneStars();
        int count = dto.getFiveStars() + dto.getFourStars() + dto.getThreeStars() + dto.getTwoStars() + dto.getOneStars();
        if (count != 0)
            dto.setAvgRating(sum / count);
        return dto;
    }

    @Override
    public Collection<CommentDTO> getOwnerComments(Long ownerId) {
        List<Review> reviews = reviewRepository.findByOwnerId(ownerId);
        Collection<CommentDTO> dtos = new ArrayList<>();
        for (Review review : reviews) {
            Guest guest = review.getGuest();
            String name = guest.getFirstName() + " " + guest.getLastName();
            dtos.add(new CommentDTO(review.getId(), name, review.getDate(), review.getComment(), review.getRate(), guest.getId()));
        }
        return dtos;
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findById(id).get();
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
