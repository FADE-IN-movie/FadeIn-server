package PINAMO.FADEIN.data.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(unique = true)
  private String userEmail;
  private String userName;
  private String userImg;

  public UserEntity(String userEmail, String userName, String userImg) {
    this.userEmail = userEmail;
    this.userName = userName;
    this.userImg = userImg;
  }

}
