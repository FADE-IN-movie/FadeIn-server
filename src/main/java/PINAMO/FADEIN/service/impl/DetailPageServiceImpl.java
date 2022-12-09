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
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.util.*;

@Service
public class DetailPageServiceImpl implements DetailPageService {

  LikeDataHandler likeDataHandler;
  UserDataHandler userDataHandler;
  ContentDataHandler contentDataHandler;
  ContentGenreDataHandler contentGenreDataHandler;

  RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
  MovieUtil movieUtil = new MovieUtil();

  @Autowired
  public DetailPageServiceImpl(LikeDataHandler likeDataHandler, UserDataHandler userDataHandler, ContentDataHandler contentDataHandler, ContentGenreDataHandler contentGenreDataHandler) {
    this.likeDataHandler = likeDataHandler;
    this.userDataHandler = userDataHandler;
    this.contentDataHandler = contentDataHandler;
    this.contentGenreDataHandler = contentGenreDataHandler;
  }

  @Override
  public DetailObject getDetail(String path, String type) {

    try {
      String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
      JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

      int contentId = parser.getInt("id");
      String title;
      String originalTitle;
      String poster = movieUtil.posterTransducer(parser.get("poster_path"), "poster");
      String backdrop = movieUtil.posterTransducer(parser.get("backdrop_path"), "backdrop");
      String releaseDate;
      String country;
      String runtime;
      String certification;

      ArrayList<String> genre = movieUtil.GenreTransducerByName(parser.getJSONArray("genres"));

      if (type.equals("movie")) {
        title = parser.getString("title");
        originalTitle = parser.getString("original_title");
        releaseDate = parser.getString("release_date");

        country = movieUtil.countryTransducer(parser.getJSONArray("production_countries"), type);
        certification = movieUtil.getMovieCertification(contentId);

        runtime = Integer.toString(parser.getInt("runtime"));
      }
      else {
        title = parser.getString("name");
        originalTitle = parser.getString("original_name");
        releaseDate = parser.getString("first_air_date");

        country = movieUtil.countryTransducer(parser.getJSONArray("origin_country"), type);
        certification = movieUtil.getTvCertification(contentId);

        JSONArray runtimeArray = parser.getJSONArray("episode_run_time");
        if (runtimeArray.length() != 0) runtime = Objects.toString(runtimeArray.get(0));
        else runtime = null;
      }

      String rating = parser.get("vote_average").toString();
      String overview = parser.getString("overview");

      return new DetailObject(contentId, title, originalTitle, poster, backdrop, releaseDate, genre, country, runtime, certification, rating, overview);
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<CastObject> getCast(String path) {
    try {
      path = path + "/" + "credits";

      String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
      JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

      JSONArray crews = parser.getJSONArray("crew");

      List<CastObject> returnCast = new ArrayList<>();

      int crewSize;
      int crewLength = crews.length();

      if (crewLength < 5) crewSize = crewLength;
      else crewSize = 5;

      if (crewSize != 0) {
        for (int i = 0; i < crewSize; i++) {
          JSONObject crew = (JSONObject) crews.get(i);

          String job = crew.getString("job");

          if (job.equals("Director")) {
            String name = crew.getString("name");

            String profile = movieUtil.profileImageTransducer(crew.get("profile_path"), crew.getInt("gender"));

            CastObject castObject = new CastObject(name, job, profile);

            returnCast.add(castObject);

            break;
          }
        }
      }

      JSONArray casts = parser.getJSONArray("cast");

      int castSize;
      int castLength = casts.length();

      if (castLength < 5) castSize = casts.length();
      else castSize = 5;

      if (castSize != 0) {
        for (int i = 0; i < castSize; i++) {
          JSONObject cast = (JSONObject) casts.get(i);

          String name = cast.getString("name");
          String role = cast.getString("character");

          String profile = movieUtil.profileImageTransducer(cast.get("profile_path"), cast.getInt("gender"));

          CastObject castObject = new CastObject(name, role, profile);

          returnCast.add(castObject);
        }
      }
      return returnCast;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<ContentObject> getSimilarContents(String path) {
    try {
      path = path + "/" + "recommendations";

      String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko&page=1", path);
      JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

      JSONArray similarMovies = parser.getJSONArray("results");

      List<ContentObject> returnSimilarMovies = new ArrayList<>();

      int similarContentSize;
      int similarContentLength = similarMovies.length();

      if (similarContentLength < 5) similarContentSize =  similarContentLength;
      else similarContentSize = 5;

      if (similarContentSize != 0) {
        for (int i = 0; i < similarContentSize; i++) {
          JSONObject movie = (JSONObject) similarMovies.get(i);

          int id = movie.getInt("id");
          String type = path.split("/")[0];
          String title;

          if (type.equals("movie")) title = movie.getString("title");
          else title = movie.getString("name");

          ArrayList<String> return_genres = movieUtil.GenreTransducer(movie.getJSONArray("genre_ids"));

          String poster = movieUtil.posterTransducer(movie.get("poster_path"), "poster");

          String overview = movie.getString("overview");

          ContentObject ContentObject = new ContentObject(id, type, title, return_genres, poster, overview);

          returnSimilarMovies.add(ContentObject);
        }
      }
      return returnSimilarMovies;
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
