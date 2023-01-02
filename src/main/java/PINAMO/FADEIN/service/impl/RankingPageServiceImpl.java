package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.dto.movie.RankingPageDTO;
import PINAMO.FADEIN.handler.ContentDataHandler;
import PINAMO.FADEIN.handler.ContentGenreDataHandler;
import PINAMO.FADEIN.handler.LikeDataHandler;
import PINAMO.FADEIN.handler.UserDataHandler;
import PINAMO.FADEIN.service.RankingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PINAMO.FADEIN.utils.MovieUtil;

@Service
public class RankingPageServiceImpl implements RankingPageService {

  MovieUtil movieUtil;

  @Autowired
  public RankingPageServiceImpl(MovieUtil movieUtil) {
    this.movieUtil = movieUtil;
  }

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
