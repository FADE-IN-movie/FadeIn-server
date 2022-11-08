package PINAMO.FADEIN.data.object;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class detailObject {

  private int id;
  private String title;
  private String originalTitle;
  private String poster;
  private String backdrop;
  private String releaseDate;
  private List<String> genre;
  private String country;
  private String runtime;
  private String certification;
  private String rating;
  private String overview;

}
