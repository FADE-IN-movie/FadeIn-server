package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentEntity;

import java.util.List;

public interface ContentDataHandler {

  ContentEntity saveContentEntity(int tmdbId, String type, String title, String genre, String poster, String overview);

  ContentEntity getContentEntityByTmdbId(int tmdbId);
  List<ContentEntity> getContentEntitiesByType(String type);

  Boolean isContentEntityByTmdbId(int tmdbId);

}
