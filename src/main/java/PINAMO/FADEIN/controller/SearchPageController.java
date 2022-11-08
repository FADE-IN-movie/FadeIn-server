package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.service.MainPageService;
import PINAMO.FADEIN.service.SearchPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchPageController {

  private SearchPageService searchPageService;

  private final Logger LOGGER = LoggerFactory.getLogger(SearchPageController.class);

  @Autowired
  public SearchPageController(SearchPageService searchPageService) {
    this.searchPageService = searchPageService;
  }

  @GetMapping(value = "")
  public SearchPageDTO getSearchPage(@RequestParam String type, @RequestParam String keyword) {

    LOGGER.info("GET SEARCH RESULTS.");

    return searchPageService.getSearchPage(type, keyword);
  }

}
