package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentEntity;

public interface ContentDataHandler {

  ContentEntity saveContentEntity(int tmdbId, String type, String title, String genre, String poster, String overview);

  ContentEntity getContentEntity(int tmdbId);

  Boolean isContentEntityByTmdbId(int tmdbId);

}
