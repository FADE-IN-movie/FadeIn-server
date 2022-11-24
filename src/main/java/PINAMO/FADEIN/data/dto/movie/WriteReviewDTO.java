package PINAMO.FADEIN.data.dto.movie;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WriteReviewDTO {

  private int contentId;
  private String type;
  private String watchedAt;
  private String watchedIn;
  private String watchedWith;
  private float rating;
  private String memo;
  private String comment;

}
