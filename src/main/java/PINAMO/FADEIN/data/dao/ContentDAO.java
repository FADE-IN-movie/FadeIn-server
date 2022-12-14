package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;

import java.util.List;

public interface ContentDAO {
  ContentEntity saveContent(ContentEntity contentEntity);

  ContentEntity getContent(Long contentId);
  ContentEntity getContentByTmdbIdAndType(int tmdbId, String isRecommended);
  List<ContentEntity> getContentsByType(String type);

  Boolean isContentByTmdbIdAndType(int tmdbId, String type);
  Boolean iSContentByType(String type);

}