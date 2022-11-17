package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;

import java.util.List;

public interface ContentDAO {
  ContentEntity saveContent(ContentEntity contentEntity);

  ContentEntity getContentByTmdbId(int tmdbId);
  List<ContentEntity> getContentsByType(String type);

  Boolean isContentByTmdbId(int tmdbId);
}