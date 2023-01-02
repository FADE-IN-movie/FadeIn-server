package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;

public interface WritePageService {

  WriteContentObject getWriteContent(String path);
  WriteReviewObject getWriteReview(String reviewId);
  SearchPageDTO getWriteSearch(String keyword, int page);

  int getWriteSearchLength(String keyword);

  boolean writeReview(String reviewId, Long userId, WriteReviewDTO writeReviewDTO);

}
