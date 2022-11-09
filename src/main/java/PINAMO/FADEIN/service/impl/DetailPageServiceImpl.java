package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.dto.movie.DetailPageDTO;
import PINAMO.FADEIN.data.object.castObject;
import PINAMO.FADEIN.data.object.detailObject;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.service.DetailPageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DetailPageServiceImpl implements DetailPageService {

  RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
  MovieUtil movieUtil = new MovieUtil();

  @Override
  public detailObject getDetail(String path) {

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    int contentId = parser.getInt("id");
    String title;
    String originalTitle;
    String poster = movieUtil.posterTransducer(parser.get("poster_path"));
    String backdrop = movieUtil.posterTransducer(parser.get("backdrop_path"));
    String releaseDate;
    String country;
    String runtime;
    String certification;

    ArrayList<String> genre = movieUtil.GenreTransducerByName(parser.getJSONArray("genres"));

    String type = path.split("/")[0];
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

    return new detailObject(contentId, title, originalTitle, poster, backdrop, releaseDate, genre, country, runtime, certification, rating, overview);
  }

  @Override
  public List<castObject> getCast(String path) {

    path = path + "/" + "credits";

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray crews = parser.getJSONArray("crew");

    List<castObject> returnCast = new ArrayList<>();

    if (crews.length() != 0) {
      for (int i = 0; i < crews.length(); i++) {
        JSONObject crew = (JSONObject) crews.get(i);

        String job = crew.getString("job");

        if (job.equals("Director")) {
          String name = crew.getString("name");

          String profile = movieUtil.profileImageTransducer(crew.get("profile_path"), crew.getInt("gender"));

          castObject castObject = new castObject(name, job, profile);

          returnCast.add(castObject);

          break;
        }
      }
    }

    JSONArray casts = parser.getJSONArray("cast");
    int castLength = casts.length();

    if (castLength != 0) {
      if (castLength > 5) castLength = 5;

      for (int i = 0; i < castLength; i++) {
        JSONObject cast = (JSONObject) casts.get(i);

        String name = cast.getString("name");
        String role = cast.getString("character");

        String profile = movieUtil.profileImageTransducer(cast.get("profile_path"), cast.getInt("gender"));

        castObject castObject = new castObject(name, role, profile);

        returnCast.add(castObject);
      }
    }

    return returnCast;
  }

  @Override
  public List<movieObject> getSimilarContents(String path) {
    path = path + "/" + "recommendations";

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko&page=1", path);
    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray similarMovies = parser.getJSONArray("results");

    List<movieObject> returnSimilarMovies = new ArrayList<>();

    if (similarMovies.length() != 0) {
      for (int i = 0; i < 5; i++) {
        JSONObject movie = (JSONObject) similarMovies.get(i);

        int id = movie.getInt("id");
        String type = path.split("/")[0];
        String title;

        if (type.equals("movie")) title = movie.getString("title");
        else title = movie.getString("name");

        ArrayList<String> return_genres = movieUtil.GenreTransducer(movie.getJSONArray("genre_ids"));

        String poster = movieUtil.posterTransducer(movie.get("poster_path"));

        String overview = movie.getString("overview");

        movieObject movieObject = new movieObject(id, type, title, return_genres, poster, overview);

        returnSimilarMovies.add(movieObject);
      }
    }
    return returnSimilarMovies;
  }

  @Override
  public DetailPageDTO getDetailPage(detailObject detail, List<castObject> cast, List<movieObject> similarContents) {
    DetailPageDTO detailPageDTO = new DetailPageDTO(detail, cast, similarContents);
    return detailPageDTO;
  }

}
