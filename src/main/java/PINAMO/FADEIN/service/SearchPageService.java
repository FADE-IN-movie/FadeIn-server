package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;

public interface SearchPageService {

  SearchPageDTO getSearchPage(String type, String keyword, int page);
  SearchLengthDTO getSearchLength(String keyword);

}
