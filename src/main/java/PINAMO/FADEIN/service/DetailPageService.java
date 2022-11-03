package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.detailPageDTO;
import PINAMO.FADEIN.data.object.castObject;
import PINAMO.FADEIN.data.object.detailObject;
import PINAMO.FADEIN.data.object.movieObject;

import java.util.List;

public interface DetailPageService {
  detailObject getDetail(String path);
  List<castObject> getCast(String path);
  List<movieObject> getSimilarMovie(String path);

  detailPageDTO getDetailPage(detailObject detail, List<castObject> cast, List<movieObject> similarMovie);
}
