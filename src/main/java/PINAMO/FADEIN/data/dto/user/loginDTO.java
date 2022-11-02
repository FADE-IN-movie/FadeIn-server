package PINAMO.FADEIN.data.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class loginDTO {

  private Long id;
  private String userEmail;
  private String userName;
  private String userImg;

  private String accessToken;
  private String accessTokenExp;
  private String refreshToken;
  private String refreshTokenExp;

  public loginDTO(Long id, String userEmail, String userName, String userImg) {
  }
}
