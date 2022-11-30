package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.dto.movie.WriteSearchDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;

import java.util.Map;

public interface WritePageService {

  WriteContentObject getWriteContent(String path);
  WriteReviewObject getWriteReview(String reviewId);
  WriteSearchDTO getWriteSearch(String keyword, int page);

  boolean writeReview(String reviewId, Long userId, WriteReviewDTO writeReviewDTO);

}
