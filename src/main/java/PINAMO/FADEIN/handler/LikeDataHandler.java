package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;

import java.util.List;

public interface LikeDataHandler {

  LikeEntity saveLikeEntity(LikeEntity likeEntity);

  LikeEntity getLikeEntity(Long userId, int tmdbId);
  List<LikeEntity> getLikeEntitiesByUserId(Long userId);

  int deleteLikeEntity(LikeEntity likeEntity);

  Boolean isLikeEntityByUserIdAndTmdbId(Long userId, int tmdbId);

}
