package PINAMO.FADEIN.data.dto.movie;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SearchLengthDTO {

  private int movieLength;
  private int tvLength;

}
