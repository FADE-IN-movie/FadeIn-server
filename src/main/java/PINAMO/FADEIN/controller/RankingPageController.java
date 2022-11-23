package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.service.SearchPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class RankingPageController {

  private SearchPageService searchPageService;

  private final Logger LOGGER = LoggerFactory.getLogger(RankingPageController.class);

  @Autowired
  public RankingPageController(SearchPageService searchPageService) {
    this.searchPageService = searchPageService;
  }

  @GetMapping(value = "")
  public SearchPageDTO getSearchPage(@RequestParam String type, @RequestParam String keyword, @RequestParam int page) {

    LOGGER.info("GET SEARCH RESULTS.");

    return searchPageService.getSearchPage(type, keyword, page);
  }

  @GetMapping(value = "/length")
  public SearchLengthDTO getSearchLength(@RequestParam String keyword) {

    LOGGER.info("GET SEARCH LENGTH.");

    return searchPageService.getSearchLength(keyword);
  }

}
