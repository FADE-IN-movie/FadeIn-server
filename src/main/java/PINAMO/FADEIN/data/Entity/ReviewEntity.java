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
  private String id;
  @ManyToOne
  @JoinColumn(name ="userId", referencedColumnName = "id")
  private UserEntity userEntity;
  @ManyToOne
  @JoinColumn(name ="contentId", referencedColumnName = "id")
  private ContentEntity contentEntity;
  @Column(columnDefinition = "DATE")
  private String watchedDate;
  @Column(columnDefinition = "TIME")
  private String watchedTime;
  private String watchedIn;
  private String watchedWith;
  @Column(columnDefinition = "DECIMAL(3,1) NOT NULL")
  private float rating;
  @Column(columnDefinition = "TEXT")
  private String memo;
  @Column(columnDefinition = "TEXT")
  private String comment;

  public ReviewEntity(UserEntity userEntity, ContentEntity contentEntity, String watchedDate, String watchedTime, String watchedIn, String watchedWith, float rating, String memo, String comment) {
    this.userEntity = userEntity;
    this.contentEntity = contentEntity;
    this.watchedDate = watchedDate;
    this.watchedTime = watchedTime;
    this.watchedIn = watchedIn;
    this.watchedWith = watchedWith;
    this.rating = rating;
    this.memo = memo;
    this.comment = comment;
  }

}
