package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.DetailPageDTO;
import PINAMO.FADEIN.data.dto.movie.LikeDTO;
import PINAMO.FADEIN.data.object.castObject;
import PINAMO.FADEIN.data.object.detailObject;
import PINAMO.FADEIN.data.object.movieObject;

import java.util.List;
import java.util.Map;

public interface DetailPageService {

  detailObject getDetail(String path);
  List<castObject> getCast(String path);
  List<movieObject> getSimilarContents(String path);

  DetailPageDTO getDetailPage(detailObject detail, List<castObject> cast, List<movieObject> getSimilarContents);

  LikeDTO changeLikeState(LikeDTO likeDTO, Long userId);
}
