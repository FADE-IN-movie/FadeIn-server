package PINAMO.FADEIN.handler.impl;

import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.dao.ContentGenreDAO;
import PINAMO.FADEIN.data.dao.LikeDAO;
import PINAMO.FADEIN.handler.ContentDataHandler;
import PINAMO.FADEIN.handler.ContentGenreDataHandler;
import PINAMO.FADEIN.handler.LikeDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ContentGenreDataHandlerImpl implements ContentGenreDataHandler {

  ContentGenreDAO contentGenreDAO;

  @Autowired
  public ContentGenreDataHandlerImpl(ContentGenreDAO contentGenreDAO) {
    this.contentGenreDAO = contentGenreDAO;
  }

  @Override
  public ContentGenreEntity saveContentGenreEntity(ContentGenreEntity contentGenreEntity) {
    return contentGenreDAO.saveContentGenre(contentGenreEntity);
  }

  @Override
  public ArrayList<ContentGenreEntity> getContentGenreEntitiesByContentId(Long contentId) {
    return contentGenreDAO.getContentGenresByContentId(contentId);
  }

}
