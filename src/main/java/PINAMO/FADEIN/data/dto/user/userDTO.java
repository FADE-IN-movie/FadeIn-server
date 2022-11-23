package PINAMO.FADEIN.data.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class userDTO {

  private Long id;
  private String userEmail;
  private String userName;
  private String userImg;

}
