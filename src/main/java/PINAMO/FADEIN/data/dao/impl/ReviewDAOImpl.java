package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.dao.LikeDAO;
import PINAMO.FADEIN.data.dao.ReviewDAO;
import PINAMO.FADEIN.data.repository.LikeRepository;
import PINAMO.FADEIN.data.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewDAOImpl implements ReviewDAO {

    ReviewRepository reviewRepository;

    @Autowired
    public ReviewDAOImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public boolean isReview(String reviewId) {
        return reviewRepository.existsById(reviewId);
    }

    @Override
    public boolean isReviewByUserId(Long userId) {
        return reviewRepository.existsByUserEntity_Id(userId);
    }

    @Override
    public ReviewEntity getReview(String reviewId) {
        return reviewRepository.getReferenceById(reviewId);
    }

    @Override
    public ReviewEntity getReviewByUserIdAndContentId(Long userId, Long contentId) {
        return reviewRepository.findByUserEntity_IdAndContentEntity_Id(userId, contentId);
    }

    @Override
    public List<ReviewEntity> getReviewsByUserId(Long userId) {
        return reviewRepository.findAllByUserEntity_Id(userId);
    }

    @Override
    public List<ReviewEntity> getReviewsByUserIdAndDate(Long userId, String start, String end) {
        return reviewRepository.findAllByUserIdAnAndWatchedDate(userId, start, end);
    }

    @Override
    public ReviewEntity saveReview(ReviewEntity reviewEntity) {
        return reviewRepository.save(reviewEntity);
    }

    @Override
    public void deleteReview(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public int updateReview(String reviewId, String watched_date, String watched_time, String watched_in, String watched_with, float rating, String memo, String comment) {
        return reviewRepository.updateReview(reviewId, watched_date, watched_time, watched_in, watched_with, rating, memo, comment);
    }
}
