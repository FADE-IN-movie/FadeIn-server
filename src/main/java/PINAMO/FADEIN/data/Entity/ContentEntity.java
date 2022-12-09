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
  @Column(columnDefinition = "VARCHAR(10)")
  private String type;
  @Column(columnDefinition = "VARCHAR(100)")
  private String title;
  @Column(columnDefinition = "VARCHAR(100)")
  private String originalTitle;
  @Column(columnDefinition = "VARCHAR(100)")
  private String poster;
  private int runtime;
  @Column(columnDefinition = "TEXT")
  private String overview;
  private String isRecommended;

  public ContentEntity(int tmdbId, String type, String title, String originalTitle, String poster, int runtime, String overview, String isRecommended) {
    this.tmdbId = tmdbId;
    this.type = type;
    this.title = title;
    this.originalTitle = originalTitle;
    this.poster = poster;
    this.runtime = runtime;
    this.overview = overview;
    this.isRecommended = isRecommended;
  }

}
