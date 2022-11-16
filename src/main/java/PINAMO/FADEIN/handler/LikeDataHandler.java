package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;

public interface LikeDataHandler {

  LikeEntity saveLikeEntity(LikeEntity likeEntity);

  LikeEntity getLikeEntity(Long userId, int tmdbId);

  int deleteLikeEntity(LikeEntity likeEntity);

  Boolean isLikeEntityByUserIdAndTmdbId(Long userId, int tmdbId);

}
