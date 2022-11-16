package PINAMO.FADEIN.data.dto.movie;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LikeDTO {

  private int tmdbId;
  private String type;
  private boolean currentState;

}
