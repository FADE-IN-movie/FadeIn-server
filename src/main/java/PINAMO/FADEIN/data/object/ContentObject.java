package PINAMO.FADEIN.data.object;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ContentObject {
  private int id;
  private int rank;
  private String type;
  private String title;
  private List<String> genre;
  private String poster;
  private String overview;

  public ContentObject(int id, String type, String title, List<String> genre, String poster, String overview) {
    this.id = id;
    this.type = type;
    this.title = title;
    this.genre = genre;
    this.poster = poster;
    this.overview = overview;
  }
}