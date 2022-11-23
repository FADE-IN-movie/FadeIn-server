package PINAMO.FADEIN.data.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reviews")
public class ReviewEntity {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name ="userId", referencedColumnName = "id")
  private UserEntity userEntity;
  @ManyToOne
  @JoinColumn(name ="contentId", referencedColumnName = "id")
  private ContentEntity contentEntity;
  @Column(columnDefinition = "DATETIME")
  private String watched_at;
  private String watched_in;
  private String watched_with;
  @Column(columnDefinition = "DECIMAL(3,1) NOT NULL")
  private float rating;
  @Column(columnDefinition = "TEXT")
  private String memo;
  @Column(columnDefinition = "TEXT")
  private String comment;

  public ReviewEntity(UserEntity userEntity, ContentEntity contentEntity, String watched_at, String watched_in, String watched_with, float rating, String memo, String comment) {
    this.userEntity = userEntity;
    this.contentEntity = contentEntity;
    this.watched_at = watched_at;
    this.watched_in = watched_in;
    this.watched_with = watched_with;
    this.rating = rating;
    this.memo = memo;
    this.comment = comment;
  }

}
