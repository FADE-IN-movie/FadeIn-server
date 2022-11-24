package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WritePageDTO {

  private WriteContentObject content;
  private WriteReviewObject review;

}

