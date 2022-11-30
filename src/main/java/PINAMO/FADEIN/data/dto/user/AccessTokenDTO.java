package PINAMO.FADEIN.data.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AccessTokenDTO {
  private String accessToken;
  private String accessTokenExp;
}
