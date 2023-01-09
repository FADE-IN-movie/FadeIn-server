package PINAMO.FADEIN.utils;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.object.CastObject;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.object.DetailObject;
import PINAMO.FADEIN.data.object.WriteContentObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class MovieUtil {

  @Value("${tmdb.api.key}")
  public String tmdbKey;

  public JSONObject GetRestTemplate(String url) {

    URI uri = UriComponentsBuilder
        .fromUriString(url)
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> requestEntity = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

    JSONObject parser = new JSONObject(requestEntity.getBody());

    return parser;
  }

  public ArrayList<String> GenreTransducer(JSONArray genres){
    ArrayList<String> returnArrayList = new ArrayList<>();

    for (int i = 0; i < genres.length(); i++) {
      switch ((int) genres.get(i)) {
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

  public String GenreReverseTransducer(String genre){

    String returnGenre = new String();

      switch (genre) {
        case "액션":
          returnGenre = "28";
          break;
        case "모험":
          returnGenre =  "12";
          break;
        case "애니메이션":
          returnGenre =  "16";
          break;
        case "코미디":
          returnGenre =  "35";
          break;
        case "범죄":
          returnGenre =  "80";
          break;
        case "다큐멘터리":
          returnGenre =  "99";
          break;
        case "드라마":
          returnGenre =  "18";
          break;
        case "가족":
          returnGenre =  "10751";
          break;
        case "판타지":
          returnGenre =  "14";
          break;
        case "역사":
          returnGenre =  "36";
          break;
        case "공포":
          returnGenre =  "27";
          break;
        case "음악":
          returnGenre =  "10402";
          break;
        case "미스터리":
          returnGenre =  "9648";
          break;
        case "로맨스":
          returnGenre =  "10749";
          break;
        case "SF":
          returnGenre =  "878";
          break;
        case "TV영화":
          returnGenre =  "10770";
          break;
        case "스릴러":
          returnGenre =  "53";
          break;
        case "전쟁":
          returnGenre =  "10752";
          break;
        case "서부":
          returnGenre =  "37";
          break;
        case "액션&모험":
          returnGenre =  "10759";
          break;
        case "키즈":
          returnGenre =  "10762";
          break;
        case "뉴스":
          returnGenre =  "10763";
          break;
        case "리얼리티":
          returnGenre =  "10764";
          break;
        case "SF판타지":
          returnGenre =  "10765";
          break;
        case "연속극":
          returnGenre =  "10766";
          break;
        case "토크":
          returnGenre =  "10767";
          break;
        case "전쟁&정치":
          returnGenre =  "10768";
          break;
      }

      return returnGenre;
  }

  public ArrayList<String> GenreTransducerByName(JSONArray genreList) {

    JSONArray genreIds = new JSONArray();

    if (genreList.length() != 0) {
      for (int i = 0; i < genreList.length(); i++) {
        JSONObject genreObject = (JSONObject) genreList.get(i);
        int genreId = genreObject.getInt("id");
        genreIds.put(genreId);
      }
    }
    ArrayList<String> returnGenre = GenreTransducer(genreIds);

    return returnGenre;
  }

  public String profileImageTransducer(Object profilePath, int gender) {
    String profile;
    if (!profilePath.equals(null)) {
      profile = "https://image.tmdb.org/t/p/w185" + profilePath;
    }
    else {
      if (gender == 1)
        profile = "https://www.themoviedb.org/assets/2/v4/glyphicons/basic/glyphicons-basic-4-user-grey-d8fe957375e70239d6abdd549fd7568c89281b2179b5f4470e2e12895792dfa5.svg";
      else
        profile = "https://www.themoviedb.org/assets/2/v4/glyphicons/basic/glyphicons-basic-36-user-female-grey-d9222f16ec16a33ed5e2c9bbdca07a4c48db14008bbebbabced8f8ed1fa2ad59.svg";
    }
    return profile;
  }

  public String overviewTransducer(String type, int movieId) {
    String path = type+"/"+movieId;

    String url = String.format("https://api.themoviedb.org/3/%s?api_key=%s&language=en-US", path, tmdbKey);

    JSONObject parser = GetRestTemplate(url);

    String overview = parser.getString("overview");

    return overview;
  }

  public String posterTransducer(Object poster_path, String type) {
    String poster;

    if (poster_path.equals(null)) {
      if (type.equals("backdrop")) poster = "";
      else poster = "https://www.themoviedb.org/assets/2/v4/glyphicons/basic/glyphicons-basic-38-picture-grey-c2ebdbb057f2a7614185931650f8cee23fa137b93812ccb132b9df511df1cfac.svg";
    }
    else {
      if (type.equals("backdrop")) poster = "https://image.tmdb.org/t/p/w780" + poster_path;
      else poster = "https://image.tmdb.org/t/p/w342" + poster_path;
    }
    return poster;
  }

  public String countryTransducer(JSONArray countryArray, String type) {
    String country;

    if (countryArray.length() != 0) {
      if (type.equals("movie")) {
        JSONObject countryObject = (JSONObject) countryArray.get(0);
        country = countryObject.getString("iso_3166_1"); // 국가코드를 나라이름으로 바꾸는 함수 필요
      }
      else country = countryArray.get(0).toString();// 국가코드를 나라이름으로 바꾸는 함수 필요
    }
    else country = null;

    return country;
  }

  public List<ContentObject> getMovies(String type, String menu, int page, String query) {
    String path;
    int size = 10;

    if (menu.equals("search") || menu.equals("discover")) path = menu + "/" + type;
    else path = type + "/" + menu;

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=%s&language=ko%s&page=%d", path, tmdbKey, query, page);

    JSONObject parser = GetRestTemplate(requestURL);

    JSONArray arrayList = parser.getJSONArray("results");

    List<ContentObject> return_movies = new ArrayList<>();

    if (arrayList.length() != 0) {
      if (menu.equals("search") || menu.equals("discover")) size = arrayList.length();

      for (int i = 0; i < size; i++) {
        JSONObject detail = (JSONObject) arrayList.get(i);

        MovieUtil movieUtil = new MovieUtil();

        int movieId = detail.getInt("id");

        String title;

        String mediaType;
        if (type.equals("multi")) mediaType = detail.getString("media_type");
        else mediaType = type;

        if (!mediaType.equals("movie") && !mediaType.equals("tv")) {
          ContentObject movie = new ContentObject(0, "movie", "", null, "", "");

          return_movies.add(movie);
        }
        else {
          if (mediaType.equals("movie")) title = detail.getString("title");
          else title = detail.getString("name");

          String overview = detail.getString("overview");

          if (overview.equals("")) overview = overviewTransducer(mediaType, movieId);

          ArrayList<String> return_genres = GenreTransducer(detail.getJSONArray("genre_ids"));

          String poster = posterTransducer(detail.get("poster_path"), "poster");

          ContentObject movie;

          if (menu.equals("popular") || menu.equals("top_rated")) {
            movie = new ContentObject(movieId, i + 1, mediaType, title, return_genres, poster, overview);
          }
          else movie = new ContentObject(movieId, mediaType, title, return_genres, poster, overview);

          return_movies.add(movie);
        }
      }
    }
    return return_movies;
  }

  public DetailObject getDetail(String path, String type) {
    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=%s&language=ko", path, tmdbKey);
    JSONObject parser = GetRestTemplate(requestURL);

    int contentId = parser.getInt("id");
    String title;
    String originalTitle;
    String poster = posterTransducer(parser.get("poster_path"), "poster");
    String backdrop = posterTransducer(parser.get("backdrop_path"), "backdrop");
    String releaseDate;
    String country;
    String runtime;
    String certification;

    ArrayList<String> genre = GenreTransducerByName(parser.getJSONArray("genres"));

    if (type.equals("movie")) {
      title = parser.getString("title");
      originalTitle = parser.getString("original_title");
      releaseDate = parser.getString("release_date");

      country = countryTransducer(parser.getJSONArray("production_countries"), type);
      certification = getMovieCertification(contentId);

      runtime = Integer.toString(parser.getInt("runtime"));
    }
    else {
      title = parser.getString("name");
      originalTitle = parser.getString("original_name");
      releaseDate = parser.getString("first_air_date");

      country = countryTransducer(parser.getJSONArray("origin_country"), type);
      certification = getTvCertification(contentId);

      JSONArray runtimeArray = parser.getJSONArray("episode_run_time");
      if (runtimeArray.length() != 0) runtime = Objects.toString(runtimeArray.get(0));
      else runtime = null;
    }

    String rating = parser.get("vote_average").toString();
    String overview = parser.getString("overview");

    return new DetailObject(contentId, title, originalTitle, poster, backdrop, releaseDate, genre, country, runtime, certification, rating, overview);
  }

  public List<CastObject> getCast(String path) {
    path = path + "/" + "credits";

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=%s&language=ko", path, tmdbKey);
    JSONObject parser = GetRestTemplate(requestURL);

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

          String profile = profileImageTransducer(crew.get("profile_path"), crew.getInt("gender"));

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

        String profile = profileImageTransducer(cast.get("profile_path"), cast.getInt("gender"));

        CastObject castObject = new CastObject(name, role, profile);

        returnCast.add(castObject);
      }
    }
    return returnCast;
  }

  public List<ContentObject> getSimilarContents(String path) {
    path = path + "/" + "recommendations";

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=%s&language=ko&page=1", path, tmdbKey);
    JSONObject parser = GetRestTemplate(requestURL);

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

        ArrayList<String> return_genres = GenreTransducer(movie.getJSONArray("genre_ids"));

        String poster = posterTransducer(movie.get("poster_path"), "poster");

        String overview = movie.getString("overview");

        ContentObject ContentObject = new ContentObject(id, type, title, return_genres, poster, overview);

        returnSimilarMovies.add(ContentObject);
      }
    }
    return returnSimilarMovies;
  }

  public String getMovieCertification(int movieId) {

    String requestURL = String.format("https://api.themoviedb.org/3/movie/%s/release_dates?api_key=%s", movieId, tmdbKey);

    JSONObject parser = GetRestTemplate(requestURL);

    JSONArray arrayList = parser.getJSONArray("results");

    String krCertification = "";
    String usCertification = "";

    if (arrayList.length() != 0) {
      for (int i = 0; i < arrayList.length(); i++) {
        JSONObject temp = (JSONObject) arrayList.get(i);

        String countryCode = temp.getString("iso_3166_1");
        JSONArray certificationList = temp.getJSONArray("release_dates");
        JSONObject certificationObject = (JSONObject) certificationList.get(0);
        String certification = certificationObject.getString("certification");

        if ((countryCode.equals("KR") && !certification.equals(""))) krCertification = certification;
        else if (countryCode.equals("US")) usCertification = certification;
      }
    }
    if (!krCertification.isEmpty()) return krCertification;
    else return usCertification;
  }

  public String getTvCertification(int tvId) {

    String requestURL = String.format("https://api.themoviedb.org/3/tv/%s/content_ratings?api_key=%s&language=ko", tvId, tmdbKey);

    JSONObject parser = GetRestTemplate(requestURL);

    JSONArray arrayList = parser.getJSONArray("results");

    String krCertification = "";
    String usCertification = "";

    if (arrayList.length() != 0) {
      for (int i = 0; i < arrayList.length(); i++) {
        JSONObject result = (JSONObject) arrayList.get(i);

        String countryCode = result.getString("iso_3166_1");

        if (countryCode.equals("KR")) krCertification = result.getString("rating");
        else if (countryCode.equals("US")) usCertification = result.getString("rating");
      }
    }

    if (!krCertification.equals(null)) return krCertification;
    else return usCertification;
  }

  public SearchLengthDTO getSearchLength(String keyword) {

    String query = "&query=" + keyword;

    String requestURL = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&language=ko%s&page=1", tmdbKey, query);
    JSONObject parser = GetRestTemplate(requestURL);

    Object Object = parser.get("total_results");
    int movieLength = 0;

    if (Object != null) {
      movieLength = (int) Object;
    }

    requestURL = String.format("https://api.themoviedb.org/3/search/tv?api_key=%s&language=ko%s&page=1", tmdbKey, query);
    parser = GetRestTemplate(requestURL);

    Object = parser.get("total_results");
    int tvLength = 0;

    if (Object != null) {
      tvLength = (int) Object;
    }

    SearchLengthDTO searchLengthDTO = new SearchLengthDTO(movieLength, tvLength);

    return searchLengthDTO;
  }

  public WriteContentObject getWriteContent(String path) {
    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=%s&language=ko", path, tmdbKey);
    JSONObject parser = GetRestTemplate(requestURL);

    int tmdbId = parser.getInt("id");
    String title;
    String originalTitle;
    String poster = posterTransducer(parser.get("poster_path"), "poster");
    String backdrop = posterTransducer(parser.get("backdrop_path"), "backdrop");
    String type = path.split("/")[0];
    if (type.equals("movie")) {
      title = parser.getString("title");
      originalTitle = parser.getString("original_title");
    }
    else {
      title = parser.getString("name");
      originalTitle = parser.getString("original_name");
    }
    WriteContentObject writeContentObject = new WriteContentObject(tmdbId, title, originalTitle, poster,backdrop);

    return writeContentObject;
  }

  public int getWriteSearchLength(String keyword) {

    String query = "&query=" + keyword;

    String requestURL = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&language=ko%s&page=1", tmdbKey, query);
    JSONObject parser = GetRestTemplate(requestURL);

    Object Object = parser.get("total_results");
    int movieLength = 0;

    if (Object != null) {
      movieLength = (int) Object;
    }

    requestURL = String.format("https://api.themoviedb.org/3/search/tv?api_key=%s&language=ko%s&page=1", tmdbKey, query);
    parser = GetRestTemplate(requestURL);

    Object = parser.get("total_results");
    int tvLength = 0;

    if (Object != null) {
      tvLength = (int) Object;
    }
    
    return movieLength+tvLength;
  }

  public Map<ContentEntity,ArrayList<String>> getContentByEntity(String type, String path, String isRecommended) {

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=%s&language=ko", path, tmdbKey);

    JSONObject parser = GetRestTemplate(requestURL);

    int tmdbId = parser.getInt("id");
    String poster = posterTransducer(parser.get("poster_path"), "poster");
    String overview = parser.getString("overview");
    ArrayList<String> genre = GenreTransducerByName(parser.getJSONArray("genres"));

    String title;
    String originalTitle;
    int runtime;

    if (type.equals("movie")) {
      title = parser.getString("title");
      originalTitle = parser.getString("original_title");
      runtime = parser.getInt("runtime");
    }
    else {
      title = parser.getString("name");
      originalTitle = parser.getString("original_name");
      JSONArray runtimeArray = parser.getJSONArray("episode_run_time");
      if (runtimeArray.length() != 0) runtime = Integer.parseInt(runtimeArray.get(0).toString());
      else runtime = 0;
    }

    ContentEntity contentEntity = new ContentEntity(tmdbId, type, title, originalTitle, poster, runtime, overview, isRecommended);

    Map<ContentEntity,ArrayList<String>> map = new HashMap<>();
    map.put(contentEntity, genre);

    return map;
  }

  public String getRandomGenre(String type) {
    String requestURL = String.format("https://api.themoviedb.org/3/genre/%s/list?api_key=%s&language=en-US", type, tmdbKey);
    JSONObject parser = GetRestTemplate(requestURL);

    ArrayList<String> genre = GenreTransducerByName(parser.getJSONArray("genres"));

    Random random = new Random();

    String genreId = GenreReverseTransducer(genre.get(random.nextInt(genre.size())));

    return genreId;
  }

}
