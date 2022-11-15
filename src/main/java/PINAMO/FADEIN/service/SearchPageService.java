package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.data.object.movieObject;

import java.util.List;

public interface SearchPageService {

  SearchPageDTO getSearchPage(String type, String keyword, int page);
  SearchLengthDTO getSearchLength(String keyword);

}
