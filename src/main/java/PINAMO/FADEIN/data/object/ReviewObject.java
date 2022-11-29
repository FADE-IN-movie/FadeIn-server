package PINAMO.FADEIN.data.object;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReviewObject {

  private String reviewId;
  private int tmdbId;
  private String title;
  private String originalTitle;
  private String type;
  private String poster;
  private int runtime;
  private String watchedDate;
  private String watchedTime;
  private String watchedIn;
  private String watchedWith;
  private float rating;
  private String memo;
  private String comment;

}
