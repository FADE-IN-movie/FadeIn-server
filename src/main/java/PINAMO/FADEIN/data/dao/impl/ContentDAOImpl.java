package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.dao.ContentDAO;
import PINAMO.FADEIN.data.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public ContentEntity getContent(Long contentId) {
    return contentRepository.getReferenceById(contentId);
  }

  @Override
  public ContentEntity getContentByTmdbIdAndType(int tmdbId, String type) {
    ContentEntity contentEntity = contentRepository.getContentEntityByTmdbIdAndType(tmdbId, type);
    return contentEntity;
  }

  @Override
  public List<ContentEntity> getContentsByType(String type) {
    List<ContentEntity> contentEntities = contentRepository.findAllByType(type);
    return contentEntities;
  }

  @Override
  public Boolean isContentByTmdbIdAndType(int tmdbId, String type) {
    return contentRepository.existsByTmdbIdAndType(tmdbId, type);
  }
}
