package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.castObject;
import PINAMO.FADEIN.data.object.detailObject;
import PINAMO.FADEIN.data.object.movieObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DetailPageDTO {

  private detailObject data;
  private List<castObject> cast;
  private List<movieObject> similarContent;

}
