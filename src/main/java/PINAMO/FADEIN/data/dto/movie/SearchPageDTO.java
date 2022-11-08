package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.object.movieObject;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SearchPageDTO {

  private List<movieObject> search;

}

