package PINAMO.FADEIN.data.dto.movie;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class mainPageDTO {

  private List<Movie> popular;
  private List<Movie> topRate;
  private List<Movie> nowPlaying;
  private List<Movie> preference;
  private List<Movie> recommend;

  public mainPageDTO(List<Movie> popular, List<Movie> topRate, List<Movie> nowPlaying, List<Movie> recommend) {
    this.popular = popular;
    this.topRate = topRate;
    this.nowPlaying = nowPlaying;
    this.recommend = recommend;
  }
}

