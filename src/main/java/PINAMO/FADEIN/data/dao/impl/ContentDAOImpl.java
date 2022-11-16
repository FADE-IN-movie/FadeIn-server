package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.dao.ContentDAO;
import PINAMO.FADEIN.data.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentDAOImpl implements ContentDAO {

  ContentRepository contentRepository;

  @Autowired
  public ContentDAOImpl(ContentRepository contentRepository){
    this.contentRepository = contentRepository;
  }

  @Override
  public ContentEntity saveContent(ContentEntity contentEntity) {
    contentRepository.save(contentEntity);
    return contentEntity;
  }

  @Override
  public ContentEntity getContent(int tmdbId) {
    ContentEntity contentEntity = contentRepository.getContentEntityByTmdbId(tmdbId);
    return contentEntity;
  }

  @Override
  public Boolean isContentByTmdbId(int tmdbId) {
    return contentRepository.existsByTmdbId(tmdbId);
  }
}
