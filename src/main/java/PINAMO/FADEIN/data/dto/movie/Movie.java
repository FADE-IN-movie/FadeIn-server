package PINAMO.FADEIN.data.dto.movie;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Movie {
  private int id;
  private String title;
  private List<String> genre;
  private String overview;
}