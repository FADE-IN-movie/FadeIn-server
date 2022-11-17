package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentEntity;

import java.util.List;

public interface ContentDataHandler {

  ContentEntity saveContentEntity(ContentEntity contentEntity);

  ContentEntity getContentEntity(Long contentId);
  ContentEntity getContentEntityByTmdbId(int tmdbId);
  List<ContentEntity> getContentEntitiesByType(String type);

  Boolean isContentEntityByTmdbId(int tmdbId);

}
