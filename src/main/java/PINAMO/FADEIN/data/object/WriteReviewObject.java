package PINAMO.FADEIN.data.object;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WriteReviewObject {

  private String reviewId;
  private String watched_at;
  private String watched_in;
  private String watched_with;
  private float rating;
  private String memo;
  private String comment;

}
