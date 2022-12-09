package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;

import java.util.List;

public interface LikeDAO {
  LikeEntity saveLike(LikeEntity likeEntity);

  LikeEntity getLikeByUserIdAndContentId(Long userId, Long contentId);
  List<LikeEntity> getLikesByUserId(Long userId);

  int deleteLike(LikeEntity likeEntity);

  Boolean isLikeByUserIdAndContentId(Long userId, Long contentId);
}