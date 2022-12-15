package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.controller.RankingPageController;
import PINAMO.FADEIN.data.dto.movie.RankingPageDTO;
import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.service.RankingPageService;
import PINAMO.FADEIN.service.SearchPageService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import utils.MovieUtil;
import utils.RestTemplateUtil;

@Service
public class RankingPageServiceImpl implements RankingPageService {

  MovieUtil movieUtil =  new MovieUtil();

  @Override
  public RankingPageDTO getRankingPage(String genre, String type, String sortBy, int page) {
    try {
      if (sortBy.equals("rating")) sortBy = "&sort_by=vote_average.desc";
      else if (sortBy.equals("date")) sortBy = "&sort_by=primary_released_date.desc";
      else if (sortBy.equals("popularity")) sortBy = "&sort_by=popularity.desc";

      if (!genre.equals("all")) genre = "&with_genres=" + genre;
      else genre = "";

      String query = "&vote_count.gte=300" + sortBy + genre;

      RankingPageDTO rankingPageDTO = new RankingPageDTO(movieUtil.getMovies(type, "discover", page, query));

      return rankingPageDTO;
    }
    catch (Exception e) {
      return null;
    }
  }
}
