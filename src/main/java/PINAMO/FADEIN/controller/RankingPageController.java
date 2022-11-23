package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.RankingPageDTO;
import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.service.RankingPageService;
import PINAMO.FADEIN.service.SearchPageService;
import exception.Constants;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ranking")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RankingPageController {

  private RankingPageService rankingPageService;

  private final Logger LOGGER = LoggerFactory.getLogger(RankingPageController.class);

  @Autowired
  public RankingPageController(RankingPageService rankingPageService) {
    this.rankingPageService = rankingPageService;
  }

  @GetMapping(value = "")
  public RankingPageDTO getSearchPage(@RequestParam(defaultValue = "", required = false) String genre,
                                      @RequestParam(defaultValue = "movie", required = false) String type,
                                      @RequestParam(defaultValue = "popularity", required = false) String sortBy,
                                      @RequestParam(defaultValue = "1", required = false) int page) throws CustomException{

    LOGGER.info("GET RANKING RESULTS.");

    RankingPageDTO rankingPageDTO = rankingPageService.getRankingPage(genre, type, sortBy, page);
    if (rankingPageDTO == null) {
      LOGGER.error("ERROR OCCUR IN GETTING RANKING RESULTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return rankingPageDTO;
  }
}
