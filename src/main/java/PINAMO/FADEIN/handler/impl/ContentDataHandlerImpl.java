package PINAMO.FADEIN.handler.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.dao.ContentDAO;
import PINAMO.FADEIN.handler.ContentDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ContentDataHandlerImpl implements ContentDataHandler {

  ContentDAO contentDAO;

  @Autowired
  public ContentDataHandlerImpl(ContentDAO contentDAO) {
    this.contentDAO = contentDAO;
  }

  @Override
  public ContentEntity saveContentEntity(int tmdbId, String type, String title, String genre, String poster, String overview) {
    ContentEntity contentEntity = new ContentEntity(tmdbId, type, title, genre, poster, overview);

    return contentDAO.saveContent(contentEntity);
  }

  @Override
  public ContentEntity getContentEntity(int tmdbId) {
    return contentDAO.getContent(tmdbId);
  }

  @Override
  public Boolean isContentEntityByTmdbId(int tmdbId) {
    return contentDAO.isContentByTmdbId(tmdbId);
  }

}