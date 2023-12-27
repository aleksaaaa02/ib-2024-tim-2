package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.RatingDTO;
import rs.ac.uns.ftn.Bookify.model.Review;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReviewRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReviewService;

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
        dto.setAvgRating(sum/count);
        return dto;
    }
}
