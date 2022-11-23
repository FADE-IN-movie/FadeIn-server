package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.RankingPageDTO;
import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;

public interface RankingPageService {

  RankingPageDTO getRankingPage(String genre, String type, String sortBy, int page);

}
