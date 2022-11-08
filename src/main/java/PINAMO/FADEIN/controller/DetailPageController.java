package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.DetailPageDTO;
import PINAMO.FADEIN.data.object.castObject;
import PINAMO.FADEIN.data.object.detailObject;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.service.DetailPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/contents")
public class DetailPageController {

  private DetailPageService detailPageService;

  private final Logger LOGGER = LoggerFactory.getLogger(DetailPageController.class);

  @Autowired
  public DetailPageController(DetailPageService detailPageService) {
    this.detailPageService = detailPageService;
  }

  @GetMapping(value = "/{contentId}")
  public DetailPageDTO getDetailPage(@PathVariable int contentId, @RequestParam String type) {

    String path = type + "/" + contentId;

    detailObject detail = detailPageService.getDetail(path);
    List<castObject> cast = detailPageService.getCast(path);
    List<movieObject> similarContents = detailPageService.getSimilarContents(path);

    LOGGER.info("GET CONTENT DETAIL.");

    return detailPageService.getDetailPage(detail, cast, similarContents);
  }

}
