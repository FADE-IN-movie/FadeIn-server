package PINAMO.FADEIN.data.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "likes")
public class LikeEntity {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name ="userId", referencedColumnName = "id")
  private UserEntity userEntity;
  @ManyToOne
  @JoinColumn(name ="contentId", referencedColumnName = "id")
  private ContentEntity contentEntity;

  public LikeEntity(UserEntity userEntity, ContentEntity contentEntity) {
    this.userEntity = userEntity;
    this.contentEntity = contentEntity;
  }

}
