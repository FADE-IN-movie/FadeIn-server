package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.handler.RecommendDataHandler;
import PINAMO.FADEIN.service.MainPageService;
import PINAMO.FADEIN.service.SearchPageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchPageServiceImpl implements SearchPageService {

  MovieUtil movieUtil = new MovieUtil();

  @Override
  public SearchPageDTO getSearchPage(String type, String keyword) {

    String query = "&query=" + keyword;

    SearchPageDTO searchPageDTO = new SearchPageDTO(movieUtil.getMovies(type, "search", query));

    return searchPageDTO;
  }
}
