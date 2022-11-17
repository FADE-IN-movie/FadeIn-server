package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.ContentObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MainPageDTO {

  private List<ContentObject> popular;
  private List<ContentObject> topRate;
  private List<ContentObject> nowPlaying;
  private List<ContentObject> preference;
  private List<ContentObject> recommend;

}

