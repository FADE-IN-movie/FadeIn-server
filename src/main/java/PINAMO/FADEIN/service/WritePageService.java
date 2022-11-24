package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;

import java.util.Map;

public interface WritePageService {

  WriteContentObject getWriteContent(String path);
  WriteReviewObject getWriteReview(String reviewId);

  boolean writeReview(String reviewId, Long userId, WriteReviewDTO writeReviewDTO);

}
