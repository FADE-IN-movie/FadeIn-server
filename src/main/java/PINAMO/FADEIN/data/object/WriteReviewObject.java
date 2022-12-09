package PINAMO.FADEIN.data.object;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WriteReviewObject {

  private String reviewId;
  private String watchedDate;
  private String watchedTime;
  private String watchedIn;
  private String watchedWith;
  private float rating;
  private String memo;
  private String comment;

}
