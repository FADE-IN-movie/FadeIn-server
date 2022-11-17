package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.DetailPageDTO;
import PINAMO.FADEIN.data.dto.movie.LikeDTO;
import PINAMO.FADEIN.data.object.CastObject;
import PINAMO.FADEIN.data.object.DetailObject;
import PINAMO.FADEIN.data.object.ContentObject;

import java.util.List;

public interface DetailPageService {

  DetailObject getDetail(String path);
  List<CastObject> getCast(String path);
  List<ContentObject> getSimilarContents(String path);

  DetailPageDTO getDetailPage(Long userId, DetailObject detail, List<CastObject> cast, List<ContentObject> getSimilarContents);

  LikeDTO changeLikeState(LikeDTO likeDTO, Long userId);
}
