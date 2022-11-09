package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.service.MainPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/contents")
public class MainPageController {

  private MainPageService mainPageService;

  private final Logger LOGGER = LoggerFactory.getLogger(MainPageController.class);

  @Autowired
  public MainPageController(MainPageService mainPageService) {
    this.mainPageService = mainPageService;
  }

  @GetMapping(value = "")
  public MainPageDTO getMainPage(@RequestParam String type) {
    List<movieObject> popular = mainPageService.getPopular(type);
    List<movieObject> topRate = mainPageService.getTopRate(type);
    List<movieObject> nowPlaying = mainPageService.getNowPlaying(type);
    List<movieObject> preference = mainPageService.getPopular(type);
    List<movieObject> recommend = mainPageService.getRecommend(type);
    LOGGER.info("GET "+ type.toUpperCase() + " CONTENTS.");
    return mainPageService.getMainPage(popular, topRate, nowPlaying, preference, recommend);
  }

  @PostMapping(value = "/like", headers = "HEADER")
  public void toggleLike(@RequestBody int contentId, @RequestHeader Map<String, String> header) {
    header.get("");
  }

//  @PostMapping(value = "/recommend")
//  public void postRecommendContents() {
//    mainPageService.saveRecommend();
//    LOGGER.info("SAVE RECOMMEND CONTENTS.");
//  }

}
