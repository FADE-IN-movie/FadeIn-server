package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;

public interface ContentDAO {
  ContentEntity saveContent(ContentEntity contentEntity);

  ContentEntity getContent(int tmdbId);

  Boolean isContentByTmdbId(int tmdbId);
}