package PINAMO.FADEIN.handler.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.dao.ContentDAO;
import PINAMO.FADEIN.handler.ContentDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ContentDataHandlerImpl implements ContentDataHandler {

  ContentDAO contentDAO;

  @Autowired
  public ContentDataHandlerImpl(ContentDAO contentDAO) {
    this.contentDAO = contentDAO;
  }

  @Override
  public ContentEntity saveContentEntity(ContentEntity contentEntity) {
    return contentDAO.saveContent(contentEntity);
  }

  @Override
  public ContentEntity getContentEntity(Long contentId) {
    return contentDAO.getContent(contentId);
  }

  @Override
  public ContentEntity getContentEntityByTmdbIdAndType(int tmdbId, String type) {
    return contentDAO.getContentByTmdbIdAndType(tmdbId,type);
  }

  @Override
  public List<ContentEntity> getContentEntitiesByType(String type) {
    return contentDAO.getContentsByType(type);
  }

  @Override
  public Boolean isContentEntityByTmdbIdAndType(int tmdbId, String type) {
    return contentDAO.isContentByTmdbIdAndType(tmdbId, type);
  }

}
