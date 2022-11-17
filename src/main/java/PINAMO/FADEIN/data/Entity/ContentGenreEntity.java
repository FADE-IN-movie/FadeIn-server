package PINAMO.FADEIN.data.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contentGenres")
public class ContentGenreEntity {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name ="contentId", referencedColumnName = "id")
  private ContentEntity contentEntity;
  private String genre;

  public ContentGenreEntity(ContentEntity contentEntity, String genre) {
    this.contentEntity = contentEntity;
    this.genre = genre;
  }

}
