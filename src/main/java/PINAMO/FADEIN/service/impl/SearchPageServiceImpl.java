package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.service.SearchPageService;
import org.springframework.stereotype.Service;
import utils.MovieUtil;

@Service
public class SearchPageServiceImpl implements SearchPageService {

  MovieUtil movieUtil = new MovieUtil();

  @Override
  public SearchPageDTO getSearchPage(String type, String keyword, int page) {
    try {
      String query = "&query=" + keyword;

      SearchPageDTO searchPageDTO = new SearchPageDTO(movieUtil.getMovies(type, "search", page, query));

      return searchPageDTO;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public SearchLengthDTO getSearchLength(String keyword) {
    try {
      SearchLengthDTO searchLengthDTO = movieUtil.getSearchLength(keyword);

      return searchLengthDTO;
    }
    catch (Exception e) {
      return null;
    }
  }
}
