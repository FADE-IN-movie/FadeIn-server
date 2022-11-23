package utils;

import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.object.ContentObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class MovieUtil {

  RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

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

  public ArrayList<String> GenreTransducerByName(JSONArray genreList) {
    ArrayList<String> returnGenre = new ArrayList<>();

    if (genreList.length() != 0) {
      for (int i = 0; i < genreList.length(); i++) {
        JSONObject genreObject = (JSONObject) genreList.get(i);
        String genre = genreObject.getString("name");
        returnGenre.add(genre);
      }
    }

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

    String url = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=en-US", path);

    JSONObject parser = restTemplateUtil.GetRestTemplate(url);

    String overview = parser.getString("overview");

    return overview;
  }

  public String posterTransducer(Object poster_path) {
    String poster;

    if (poster_path.equals(null)) poster = "https://www.themoviedb.org/assets/2/v4/glyphicons/basic/glyphicons-basic-38-picture-grey-c2ebdbb057f2a7614185931650f8cee23fa137b93812ccb132b9df511df1cfac.svg";
    else poster = "https://image.tmdb.org/t/p/original" + poster_path;

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

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko%s&page=%d", path, query, page);
    System.out.println(requestURL);
    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray arrayList = parser.getJSONArray("results");

    List<ContentObject> return_movies = new ArrayList<>();

    if (arrayList.length() != 0) {
      if (menu.equals("search")) size = arrayList.length();

      for (int i = 0; i < size; i++) {
        JSONObject detail = (JSONObject) arrayList.get(i);

        MovieUtil movieUtil = new MovieUtil();

        int movieId = detail.getInt("id");

        String title;

        if (type.equals("movie")) title = detail.getString("title");
        else title = detail.getString("name");

        String overview = detail.getString("overview");

        if (overview.equals("")) overview = movieUtil.overviewTransducer(type, movieId);

        ArrayList<String> return_genres = movieUtil.GenreTransducer(detail.getJSONArray("genre_ids"));

        String poster = posterTransducer(detail.get("poster_path"));

        ContentObject movie;

        if (menu.equals("popular") || menu.equals("top_rated")) {
          movie = new ContentObject(movieId, i + 1, type, title, return_genres, poster, overview);
        } else movie = new ContentObject(movieId, type, title, return_genres, poster, overview);

        return_movies.add(movie);
      }
    }
    return return_movies;
  }

  public String getMovieCertification(int movieId) {

    String requestURL = String.format("https://api.themoviedb.org/3/movie/%s/release_dates?api_key=929a001736172a3578c0d6bf3b3cbbc5", movieId);

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

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

    String requestURL = String.format("https://api.themoviedb.org/3/tv/%s/content_ratings?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", tvId);

    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

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

//  public List<movieObject> getRecommendContent(String type){
//
//    int[] movie = {20342, 372058, 101299, 70160, 207703, 343668,5876, 122906, 198663, 294254, 807, 2832, 550 ,244786 ,155 ,603 ,157336 ,6977 ,165213 ,278, 49797, 423, 398978, 530385};
//    int[] tv = {82237 ,67915 ,64840 ,117378 ,84327 ,70123 ,78648 ,80585 ,42009 ,86831 ,94796 ,97970 ,155226 ,20588 ,31505 ,90447 ,48462 ,112833 ,37722 ,1396 ,87739, 61459};
//
//    Random random = new Random();
//
//    Set<Integer> randomContentSet = new HashSet<>();
//
//    if (type.equals("movie")) {
//      while (randomContentSet.size() < 10) {
//        randomContentSet.add(movie[random.nextInt(movie.length)]);
//      }
//    }
//    else {
//      while (randomContentSet.size() < 10) {
//        randomContentSet.add(tv[random.nextInt(tv.length)]);
//      }
//    }
//
//    ArrayList<Integer> randomContent = new ArrayList<>(randomContentSet);
//
//    List<movieObject> returnContents = new ArrayList<>();
//
//    for (int i=0; i<randomContent.size(); i++) {
//
//      String path = type + "/" + randomContent.get(i);
//
//      String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
//      JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);
//
//      int id = parser.getInt("id");
//
//      String title;
//      if (type.equals("movie")) title = parser.getString("title");
//      else title = parser.getString("name");
//
//      ArrayList<String> genre = GenreTransducerByName(parser.getJSONArray("genres"));
//      String poster = posterTransducer(parser.get("poster_path"));
//      String overview = parser.getString("overview");
//
//      movieObject movieObject = new movieObject(id,type,title,genre,poster,overview);
//
//      returnContents.add(movieObject);
//    }
//    return returnContents;
//  }

  public SearchLengthDTO getSearchLength(String keyword) {

    String query = "&query=" + keyword;

    String requestURL = String.format("https://api.themoviedb.org/3/search/movie?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko%s&page=1", query);
    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    Object Object = parser.get("total_results");
    int movieLength = 0;

    if (Object != null) {
      movieLength = (int) Object;
    }

    requestURL = String.format("https://api.themoviedb.org/3/search/tv?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko%s&page=1", query);
    parser = restTemplateUtil.GetRestTemplate(requestURL);

    Object = parser.get("total_results");
    int tvLength = 0;

    if (Object != null) {
      tvLength = (int) Object;
    }

    SearchLengthDTO searchLengthDTO = new SearchLengthDTO(movieLength, tvLength);

    return searchLengthDTO;
  }
}
