package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.movieObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MainPageDTO {

  private List<movieObject> popular;
  private List<movieObject> topRate;
  private List<movieObject> nowPlaying;
  private List<movieObject> preference;
  private List<movieObject> recommend;

}

