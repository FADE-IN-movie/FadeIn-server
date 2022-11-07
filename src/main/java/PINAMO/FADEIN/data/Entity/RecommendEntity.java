package PINAMO.FADEIN.data.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recommend")
public class RecommendEntity {

  @Id
  private Long id;
  private int rank;
  private String type;
  private String title;
  private String genre;
  private String poster;
  @Column(columnDefinition = "TEXT")
  private String overview;

}
