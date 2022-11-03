package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.dto.movie.detailPageDTO;
import PINAMO.FADEIN.data.object.castObject;
import PINAMO.FADEIN.data.object.detailObject;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.handler.UserDataHandler;
import PINAMO.FADEIN.service.DetailPageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.net.URI;
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
    String title = parser.getString("title");
    String originalTitle = parser.getString("original_title");
    String poster = "https://image.tmdb.org/t/p/original" + parser.getString("poster_path");
    String backdrop = "https://image.tmdb.org/t/p/original" + parser.getString("backdrop_path");
    String releaseDate = parser.getString("release_date");
    JSONArray genreList = parser.getJSONArray("genres");
    List<String> returnGenre = new ArrayList<>();
    for (int i=0; i<genreList.length(); i++) {
      JSONObject genreObject = (JSONObject) genreList.get(i);
      String genre = genreObject.getString("name");
      returnGenre.add(genre);
    }
    JSONObject countryObject = (JSONObject) parser.getJSONArray("production_countries").get(0);
    String country = countryObject.getString("iso_3166_1"); // 국가코드를 나라이름으로 바꾸는 함수 필요
    String runtime = Integer.toString(parser.getInt("runtime"));
    String certification;

    if (path.split("/")[0].equals("movie")) certification =movieUtil.getMovieCertification(contentId);
    else certification =movieUtil.getTvCertification(contentId);

    String overview = parser.getString("overview");

    return new detailObject(contentId, title, originalTitle, poster, backdrop, releaseDate,  returnGenre, country, runtime, certification, overview);
  }

  @Override
  public List<castObject> getCast(String path) {

    path = path + "/" + "credits";

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray crews = parser.getJSONArray("crew");

    List<castObject> returnCast = new ArrayList<>();

    for (int i = 0; i < crews.length(); i++) {
      JSONObject crew = (JSONObject) crews.get(i);

      String job = crew.getString("job");

      if (job.equals("Director")) {
        String name = crew.getString("name");
        String profile = "https://image.tmdb.org/t/p/w185" + crew.getString("profile_path");
        castObject castObject = new castObject(name, "Director", profile);

        returnCast.add(castObject);

        break;
      }
    }

    JSONArray casts = parser.getJSONArray("cast");

    for (int i=0; i < 5; i++) {
      JSONObject cast = (JSONObject) casts.get(i);

      String name = cast.getString("name");
      String role = cast.getString("character");
      String profile = "https://image.tmdb.org/t/p/w185" + cast.getString("profile_path");

      castObject castObject = new castObject(name, role, profile);

      returnCast.add(castObject);
    }

    return returnCast;
  }

  @Override
  public List<movieObject> getSimilarMovie(String path) {
    path = path + "/" + "recommendations";

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko&page=1", path);

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray similarMovies = parser.getJSONArray("results");

    List<movieObject> returnSimilarMovies = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      JSONObject movie = (JSONObject) similarMovies.get(i);

      int id = movie.getInt("id");
      String type = path.split("/")[0];
      String title = movie.getString("title");

      JSONArray genre_ids = movie.getJSONArray("genre_ids");

      ArrayList<Integer> genres = new ArrayList<>();

      for (int j = 0; j<genre_ids.length(); j++) {
        genres.add((int) genre_ids.get(j));
      }

      ArrayList<String> return_genres = movieUtil.GenreTransducer(genres);

      String poster = "https://image.tmdb.org/t/p/w780" + movie.getString("poster_path");
      String overview = movie.getString("overview");

      movieObject movieObject = new movieObject(id, type, title, return_genres, poster, overview);

      returnSimilarMovies.add(movieObject);
    }
    return returnSimilarMovies;
  }

  @Override
  public detailPageDTO getDetailPage(detailObject detail, List<castObject> cast, List<movieObject> similarMovie) {
    detailPageDTO detailPageDTO = new detailPageDTO(detail, cast, similarMovie);
    return detailPageDTO;
  }

}
