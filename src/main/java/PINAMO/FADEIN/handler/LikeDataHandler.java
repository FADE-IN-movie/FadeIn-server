package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;

import java.util.List;

public interface LikeDataHandler {

  LikeEntity saveLikeEntity(LikeEntity likeEntity);

  LikeEntity getLikeEntityByUserIdAndContentId(Long userId, Long contentId);
  List<LikeEntity> getLikeEntitiesByUserId(Long userId);

  int deleteLikeEntity(LikeEntity likeEntity);

  Boolean isLikeEntityByUserIdAndContentId(Long userId, Long contentId);

}
