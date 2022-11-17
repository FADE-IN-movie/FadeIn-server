package PINAMO.FADEIN.handler.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dao.LikeDAO;
import PINAMO.FADEIN.handler.LikeDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LikeDataHandlerImpl implements LikeDataHandler {

  LikeDAO likeDAO;

  @Autowired
  public LikeDataHandlerImpl(LikeDAO likeDAO) {
    this.likeDAO = likeDAO;
  }

  @Override
  public LikeEntity saveLikeEntity(LikeEntity likeEntity) {
    return likeDAO.saveLike(likeEntity);
  }

  @Override
  public LikeEntity getLikeEntity(Long userId, int tmdbId) {
    return likeDAO.getLike(userId, tmdbId);
  }

  @Override
  public List<LikeEntity> getLikeEntitiesByUserId(Long userId) {
    return likeDAO.getLikesByUserId(userId);
  }

  @Override
  public int deleteLikeEntity(LikeEntity likeEntity) {
    return likeDAO.deleteLike(likeEntity);
  }

  @Override
  public Boolean isLikeEntityByUserIdAndTmdbId(Long userId, int tmdbId) {
    return likeDAO.isLikeByUserIdAndTmdbId(userId, tmdbId);
  }


}
