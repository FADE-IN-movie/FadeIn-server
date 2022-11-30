package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.ContentObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LikePageDTO {

  private List<ContentObject> movie;
  private List<ContentObject> tv;

}

