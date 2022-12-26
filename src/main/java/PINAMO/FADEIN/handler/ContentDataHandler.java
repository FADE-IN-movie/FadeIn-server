package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentEntity;

import java.util.List;

public interface ContentDataHandler {

  ContentEntity saveContentEntity(ContentEntity contentEntity);

  ContentEntity getContentEntity(Long contentId);
  ContentEntity getContentEntityByTmdbIdAndType(int tmdbId, String type);
  List<ContentEntity> getContentEntitiesByType(String type);

  Boolean isContentEntityByTmdbIdAndType(int tmdbId, String type);
  Boolean isContentEntityByType(String type);

}
