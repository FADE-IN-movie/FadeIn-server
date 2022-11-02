package PINAMO.FADEIN.data.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class accessTokenDTO {
  private String accessToken;
  private String accessTokenExp;
}
