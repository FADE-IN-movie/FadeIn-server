package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;

import java.util.ArrayList;
import java.util.List;

public interface ContentGenreDAO {
  ContentGenreEntity saveContentGenre(ContentGenreEntity contentGenreEntity);

  ArrayList<ContentGenreEntity> getContentGenresByContentId(Long contentId);
}