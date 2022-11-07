package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dao.RecommendDAO;
import PINAMO.FADEIN.data.repository.RecommendRepository;
import PINAMO.FADEIN.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendDAOImpl implements RecommendDAO {

  RecommendRepository recommendRepository;

  @Autowired
  public RecommendDAOImpl(RecommendRepository recommendRepository){
    this.recommendRepository = recommendRepository;
  }

  @Override
  public RecommendEntity saveRecommend(RecommendEntity recommendEntity) {
    recommendRepository.save(recommendEntity);
    return recommendEntity;
  }

  @Override
  public RecommendEntity getRecommend(Long movieId) {
    RecommendEntity recommendEntity = recommendRepository.getReferenceById(movieId);
    return recommendEntity;
  }
}
