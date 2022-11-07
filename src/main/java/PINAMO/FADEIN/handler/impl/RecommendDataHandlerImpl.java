package PINAMO.FADEIN.handler.impl;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dao.RecommendDAO;
import PINAMO.FADEIN.data.dao.UserDAO;
import PINAMO.FADEIN.handler.RecommendDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RecommendDataHandlerImpl implements RecommendDataHandler {

  RecommendDAO recommendDAO;

  @Autowired
  public RecommendDataHandlerImpl(RecommendDAO recommendDAO) {
    this.recommendDAO = recommendDAO;
  }

  @Override
  public RecommendEntity saveRecommendEntity(Long id, int rank, String type, String title, String genre, String poster, String overview) {
    RecommendEntity recommendEntity = new RecommendEntity(id, rank, type, title, genre, poster, overview);

    return recommendDAO.saveRecommend(recommendEntity);
  }

  @Override
  public RecommendEntity getRecommendEntity(Long contentId) {
    return recommendDAO.getRecommend(contentId);
  }
}
