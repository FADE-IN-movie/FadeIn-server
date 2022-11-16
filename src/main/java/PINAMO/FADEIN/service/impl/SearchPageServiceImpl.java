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

    String query = "&query=" + keyword;

    SearchPageDTO searchPageDTO = new SearchPageDTO(movieUtil.getMovies(type, "search", page, query));

    return searchPageDTO;
  }

  @Override
  public SearchLengthDTO getSearchLength(String keyword) {

    SearchLengthDTO searchLengthDTO = movieUtil.getSearchLength(keyword);

    return searchLengthDTO;
  }
}
