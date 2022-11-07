package utils;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.handler.RecommendDataHandler;
import PINAMO.FADEIN.handler.UserDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MovieUtil {
  public ArrayList<String> GenreTransducer(ArrayList<Integer> genres){
    ArrayList<String> returnArrayList = new ArrayList<>();

    for (int i = 0; i < genres.size(); i++) {
      switch (genres.get(i)) {
        case 28:
          returnArrayList.add("액션");
          break;
        case 12:
          returnArrayList.add("모험");
          break;
        case 16:
          returnArrayList.add("애니메이션");
          break;
        case 35:
          returnArrayList.add("코미디");
          break;
        case 80:
          returnArrayList.add("범죄");
          break;
        case 99:
          returnArrayList.add("다큐멘터리");
          break;
        case 18:
          returnArrayList.add("드라마");
          break;
        case 10751:
          returnArrayList.add("가족");
          break;
        case 14:
          returnArrayList.add("판타지");
          break;
        case 36:
          returnArrayList.add("역사");
          break;
        case 27:
          returnArrayList.add("공포");
          break;
        case 10402:
          returnArrayList.add("음악");
          break;
        case 9648:
          returnArrayList.add("미스터리");
          break;
        case 10749:
          returnArrayList.add("로맨스");
          break;
        case 878:
          returnArrayList.add("SF");
          break;
        case 10770:
          returnArrayList.add("TV영화");
          break;
        case 53:
          returnArrayList.add("스릴러");
          break;
        case 10752:
          returnArrayList.add("전쟁");
          break;
        case 37:
          returnArrayList.add("서부");
          break;
        case 10759:
          returnArrayList.add("액션&모험");
          break;
        case 10762:
          returnArrayList.add("키즈");
          break;
        case 10763:
          returnArrayList.add("뉴스");
          break;
        case 10764:
          returnArrayList.add("리얼리티");
          break;
        case 10765:
          returnArrayList.add("SF판타지");
          break;
        case 10766:
          returnArrayList.add("연속극");
          break;
        case 10767:
          returnArrayList.add("토크");
          break;
        case 10768:
          returnArrayList.add("전쟁&정치");
          break;
      }
    }
    return returnArrayList;
  }

  public String overviewTransducer(String type, int movieId) {
    String path = type+"/"+movieId;

    String url = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=en-US", path);

    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

    JSONObject parser = restTemplateUtil.GetRestTemplate(url);

    String overview = parser.getString("overview");

    return overview;
  }

  public List<movieObject> getMovies(String type, String menu) {
    String path = type + "/" + menu;

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko&page=1", path);

    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray arrayList = parser.getJSONArray("results");

    List<movieObject> return_movies = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      JSONObject detail = (JSONObject) arrayList.get(i);

      MovieUtil movieUtil = new MovieUtil();

      int movieId = detail.getInt("id");

      String title;

      if (type.equals("movie")) title = detail.getString("title");
      else title = detail.getString("name");

      String overview = detail.getString("overview");

      if (overview.equals("")) {

        overview = movieUtil.overviewTransducer(type, movieId);
      }
      JSONArray genre_ids = detail.getJSONArray("genre_ids");

      ArrayList<Integer> genres = new ArrayList<>();

      for (int j = 0; j<genre_ids.length(); j++) {
        genres.add((int) genre_ids.get(j));
      }

      ArrayList<String> return_genres = movieUtil.GenreTransducer(genres);

      String poster = "https://image.tmdb.org/t/p/w500" + detail.getString("poster_path");

      movieObject movie;

      if (menu.equals("popular") || menu.equals("top_rated")) {
        movie = new movieObject(movieId, i+1, type, title, return_genres, poster, overview);
      }
      else movie = new movieObject(movieId, type, title, return_genres, poster, overview);

      return_movies.add(movie);
    }

    return return_movies;
  }

  public String getMovieCertification(int movieId) {

    String requestURL = String.format("https://api.themoviedb.org/3/movie/%s/release_dates?api_key=929a001736172a3578c0d6bf3b3cbbc5", movieId);

    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray arrayList = parser.getJSONArray("results");

    String krCertification = "";
    String usCertification = "";

    for (int i=0; i<arrayList.length(); i++) {
      JSONObject temp = (JSONObject) arrayList.get(i);

      String countryCode = temp.getString("iso_3166_1");
      JSONArray certificationList = temp.getJSONArray("release_dates");
      JSONObject certificationObject = (JSONObject) certificationList.get(0);
      String certification = certificationObject.getString("certification");
      
      if ((countryCode.equals("KR") && !certification.equals(""))) krCertification = certification;
      else if (countryCode.equals("US")) usCertification = certification;
    }

    if (!krCertification.isEmpty()) return krCertification;
    else return usCertification;
  }

  public String getTvCertification(int tvId) {

    String requestURL = String.format("https://api.themoviedb.org/3/tv/%s/content_ratings?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", tvId);

    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray arrayList = parser.getJSONArray("results");

    String krCertification = "";
    String usCertification = "";

    for (int i = 0; i< arrayList.length(); i++) {
      JSONObject result = (JSONObject) arrayList.get(i);

      String countryCode = result.getString("iso_3166_1");

      if (countryCode.equals("KR")) krCertification = result.getString("rating");
      else if (countryCode.equals("US")) usCertification = result.getString("rating");
    }

    if (!krCertification.equals(null)) return krCertification;
    else return usCertification;
  }

  public RecommendEntity saveRecommend(String type, int contentId) {
    String path = type + "/" + contentId;

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);

    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    Long id = parser.getLong("id");
    String title;
    if (type.equals("movie")) title = parser.getString("title");
    else title = parser.getString("name");
    String poster = parser.getString("poster_path");
    String overview = parser.getString("overview");

    JSONArray genreList = parser.getJSONArray("genres");
    String returnGenre = "";
    for (int i=0; i<genreList.length(); i++) {
      JSONObject genreObject = (JSONObject) genreList.get(i);
      String genre = genreObject.getString("name");
      returnGenre = returnGenre + "," + genre;
    }

    RecommendEntity recommendEntity = new RecommendEntity(id, 0, type, title, returnGenre, poster, overview);

    return recommendEntity;
  }
}
