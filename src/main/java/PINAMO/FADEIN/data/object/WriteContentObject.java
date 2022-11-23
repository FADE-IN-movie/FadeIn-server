package PINAMO.FADEIN.data.object;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WriteContentObject {

  private int id;
  private String title;
  private String originalTitle;
  private String poster;
  private String backdrop;

}
