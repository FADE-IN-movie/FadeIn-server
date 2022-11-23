package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.service.SearchPageService;
import exception.Constants;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchPageController {

  private SearchPageService searchPageService;

  private final Logger LOGGER = LoggerFactory.getLogger(SearchPageController.class);

  @Autowired
  public SearchPageController(SearchPageService searchPageService) {
    this.searchPageService = searchPageService;
  }

  @GetMapping(value = "")
  public SearchPageDTO getSearchPage(@RequestParam(defaultValue = "movie", required = false) String type,
                                     @RequestParam String keyword,
                                     @RequestParam(defaultValue = "1", required = false) int page) throws CustomException {

    LOGGER.info("GET SEARCH RESULTS.");

    SearchPageDTO searchPageDTO = searchPageService.getSearchPage(type, keyword, page);
    if (searchPageDTO == null) {
      LOGGER.error("ERROR OCCUR IN GETTING SEARCH RESULTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return searchPageDTO;
  }

  @GetMapping(value = "/length")
  public SearchLengthDTO getSearchLength(@RequestParam String keyword) throws CustomException{

    LOGGER.info("GET SEARCH LENGTH.");

    SearchLengthDTO searchLengthDTO = searchPageService.getSearchLength(keyword);
    if (searchLengthDTO == null) {
      LOGGER.error("ERROR OCCUR IN GETTING SEARCH LENGTH.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return searchLengthDTO;
  }

}
