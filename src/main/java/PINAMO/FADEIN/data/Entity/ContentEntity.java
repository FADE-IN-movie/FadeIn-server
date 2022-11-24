package PINAMO.FADEIN.data.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contents")
public class ContentEntity {

  @Id
  @GeneratedValue
  private Long id;
  private int tmdbId;
  private String type;
  private String title;
  private String originalTitle;
  private String poster;
  private int runtime;
  @Column(columnDefinition = "TEXT")
  private String overview;

  public ContentEntity(int tmdbId, String type, String title, String originalTitle, String poster, int runtime, String overview) {
    this.tmdbId = tmdbId;
    this.type = type;
    this.title = title;
    this.originalTitle = originalTitle;
    this.poster = poster;
    this.runtime = runtime;
    this.overview = overview;
  }

}
