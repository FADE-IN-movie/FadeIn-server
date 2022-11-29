package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.ReviewObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReviewPageDTO {

  private List<ReviewObject> review;

}
