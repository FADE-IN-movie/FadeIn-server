package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.ReviewPageDTO;
import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;

public interface ReviewPageService {

  ReviewPageDTO getReviewPage(Long userId);

  int deleteReview(String reviewId);

}
