package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.dto.movie.LikePageDTO;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.handler.ContentDataHandler;
import PINAMO.FADEIN.handler.ContentGenreDataHandler;
import PINAMO.FADEIN.handler.LikeDataHandler;
import PINAMO.FADEIN.service.LikePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LikePageServiceImpl implements LikePageService {

  LikeDataHandler likeDataHandler;
  ContentDataHandler contentDataHandler;
  ContentGenreDataHandler contentGenreDataHandler;

  @Autowired
  public LikePageServiceImpl(ContentDataHandler contentDataHandler, LikeDataHandler likeDataHandler, ContentGenreDataHandler contentGenreDataHandler) {
    this.likeDataHandler = likeDataHandler;
    this.contentDataHandler = contentDataHandler;
    this.contentGenreDataHandler = contentGenreDataHandler;
  }

  @Override
  public LikePageDTO getLikePage(Long userId) {

    try {
      List<ContentObject> likeMovie = new ArrayList<>();
      List<ContentObject> likeTv = new ArrayList<>();

      List<LikeEntity> likeEntities = likeDataHandler.getLikeEntitiesByUserId(userId);

      for (int i=0; i<likeEntities.size(); i++) {
        ContentEntity contentEntity = likeEntities.get(i).getContentEntity();

        int tmdbId = contentEntity.getTmdbId();
        String type = contentEntity.getType();
        String title = contentEntity.getTitle();
        String poster = contentEntity.getPoster();
        String overview = contentEntity.getOverview();
        List<String> genres = new ArrayList<>();
        List<ContentGenreEntity> contentGenreEntities = contentGenreDataHandler.getContentGenreEntitiesByContentId(contentEntity.getId());
        for (int j=0; j<contentGenreEntities.size(); j++) genres.add(contentGenreEntities.get(j).getGenre());

        ContentObject contentObject = new ContentObject(tmdbId, type, title, genres, poster, overview);

        if (contentEntity.getType().equals("movie")) likeMovie.add(contentObject);
        else likeTv.add(contentObject);
      }

      Collections.reverse(likeMovie);
      Collections.reverse(likeTv);

      LikePageDTO likePageDTO = new LikePageDTO(likeMovie, likeTv);

      return likePageDTO;
    }
    catch (Exception e) {
      return null;
    }
  }
}
