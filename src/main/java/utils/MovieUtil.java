package utils;

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
}
