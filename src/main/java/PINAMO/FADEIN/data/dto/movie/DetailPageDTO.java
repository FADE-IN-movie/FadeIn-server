package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.CastObject;
import PINAMO.FADEIN.data.object.DetailObject;
import PINAMO.FADEIN.data.object.ContentObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DetailPageDTO {

  private DetailObject data;
  private List<CastObject> cast;
  private List<ContentObject> similarContent;
  private boolean currentLike;

}
