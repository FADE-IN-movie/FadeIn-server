package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;

public interface RecommendDataHandler {

  RecommendEntity saveRecommendEntity(Long id, int rank, String type, String title, String genre, String poster, String overview);

  RecommendEntity getRecommendEntity(Long contentId);

}
