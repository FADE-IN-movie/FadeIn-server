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
  private String poster;
  @Column(columnDefinition = "TEXT")
  private String overview;

  public ContentEntity(int tmdbId, String type, String title, String poster, String overview) {
    this.tmdbId = tmdbId;
    this.type = type;
    this.title = title;
    this.poster = poster;
    this.overview = overview;
  }

}
