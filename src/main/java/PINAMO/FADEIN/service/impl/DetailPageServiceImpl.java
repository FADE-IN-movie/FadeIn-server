package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.movie.DetailPageDTO;
import PINAMO.FADEIN.data.dto.movie.LikeDTO;
import PINAMO.FADEIN.data.object.CastObject;
import PINAMO.FADEIN.data.object.DetailObject;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.handler.ContentDataHandler;
import PINAMO.FADEIN.handler.ContentGenreDataHandler;
import PINAMO.FADEIN.handler.LikeDataHandler;
import PINAMO.FADEIN.handler.UserDataHandler;
import PINAMO.FADEIN.service.DetailPageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PINAMO.FADEIN.utils.MovieUtil;

import java.util.*;

@Service
public class DetailPageServiceImpl implements DetailPageService {

  MovieUtil movieUtil;
  LikeDataHandler likeDataHandler;
  UserDataHandler userDataHandler;
  ContentDataHandler contentDataHandler;
  ContentGenreDataHandler contentGenreDataHandler;

  @Autowired
  public DetailPageServiceImpl(MovieUtil movieUtil, LikeDataHandler likeDataHandler, UserDataHandler userDataHandler, ContentDataHandler contentDataHandler, ContentGenreDataHandler contentGenreDataHandler) {
    this.movieUtil = movieUtil;
    this.likeDataHandler = likeDataHandler;
    this.userDataHandler = userDataHandler;
    this.contentDataHandler = contentDataHandler;
    this.contentGenreDataHandler = contentGenreDataHandler;
  }

  @Override
  public DetailObject getDetail(String path, String type) {
    try {
      DetailObject detailObject = movieUtil.getDetail(path, type);

      return detailObject;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<CastObject> getCast(String path) {
    try {
      List<CastObject> castObjects = movieUtil.getCast(path);

      return castObjects;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<ContentObject> getSimilarContents(String path) {
    try {
      List<ContentObject> contentObjects = movieUtil.getSimilarContents(path);

      return contentObjects;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public DetailPageDTO getDetailPage(Long userId, String type, DetailObject detail, List<CastObject> cast, List<ContentObject> similarContents) {
    Boolean isContentEntity = contentDataHandler.isContentEntityByTmdbIdAndType(detail.getTmdbId(), type);

    Boolean isLike = false;

    if (isContentEntity) {
      ContentEntity contentEntity = contentDataHandler.getContentEntityByTmdbIdAndType(detail.getTmdbId(), type);
      isLike = likeDataHandler.isLikeEntityByUserIdAndContentId(userId, contentEntity.getId());
    }

    DetailPageDTO detailPageDTO = new DetailPageDTO(detail, cast, similarContents, isLike);
    return detailPageDTO;
  }

  @Override
  public LikeDTO changeLikeState(LikeDTO likeDTO, Long userId) {
    try {
      Boolean isContent = contentDataHandler.isContentEntityByTmdbIdAndType(likeDTO.getTmdbId(), likeDTO.getType());

      if (isContent) {
        ContentEntity contentEntity = contentDataHandler.getContentEntityByTmdbIdAndType(likeDTO.getTmdbId(), likeDTO.getType());
        Boolean currentLike = likeDataHandler.isLikeEntityByUserIdAndContentId(userId,contentEntity.getId());

        if (currentLike) {
          LikeEntity likeEntity = likeDataHandler.getLikeEntityByUserIdAndContentId(userId, contentEntity.getId());
          likeDataHandler.deleteLikeEntity(likeEntity);
        }
        else {
          UserEntity userEntity = userDataHandler.getUserEntity(userId);
          LikeEntity likeEntity = new LikeEntity(userEntity, contentEntity);

          Boolean isLike = likeDataHandler.isLikeEntityByUserIdAndContentId(userId, contentEntity.getId());
          if (!isLike) likeDataHandler.saveLikeEntity(likeEntity);
        }
      }
      else {
        String type = likeDTO.getType();
        String path = type + "/" + likeDTO.getTmdbId();

        Map<ContentEntity, ArrayList<String>> map = movieUtil.getContentByEntity(type, path, "no");

        ContentEntity returnContentEntity = map.keySet().iterator().next();
        ArrayList<String> genre = map.get(returnContentEntity);

        ContentEntity contentEntity = contentDataHandler.saveContentEntity(returnContentEntity);

        for (int j = 0; j < genre.size(); j++)
          contentGenreDataHandler.saveContentGenreEntity(new ContentGenreEntity(contentEntity, genre.get(j)));

        UserEntity userEntity = userDataHandler.getUserEntity(userId);
        LikeEntity likeEntity = new LikeEntity(userEntity, contentEntity);

        Boolean isLike = likeDataHandler.isLikeEntityByUserIdAndContentId(userId, contentEntity.getId());
        if (!isLike) likeDataHandler.saveLikeEntity(likeEntity);
      }

      LikeDTO return_likeDTO = new LikeDTO(likeDTO.getTmdbId(), likeDTO.getType(), !likeDTO.isCurrentLike());

      return return_likeDTO;
    }
    catch (Exception e) {
      return null;
    }
  }
}
