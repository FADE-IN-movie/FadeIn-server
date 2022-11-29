package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;

import java.util.ArrayList;
import java.util.List;

public interface ContentGenreDataHandler {

  ContentGenreEntity saveContentGenreEntity(ContentGenreEntity contentGenreEntity);

  ArrayList<ContentGenreEntity> getContentGenreEntitiesByContentId(Long contentId);

  String getReferenceGenreByUserId(Long userId, String type);
}
