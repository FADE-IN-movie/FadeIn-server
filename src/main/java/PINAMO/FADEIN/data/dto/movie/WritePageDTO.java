package PINAMO.FADEIN.data.dto.movie;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.object.CastObject;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.object.DetailObject;
import PINAMO.FADEIN.data.object.WriteContentObject;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WritePageDTO {

  private int contentId;
  private String type;
  private String watchedAt;
  private String watchedIn;
  private String watchedWith;
  private float rating;
  private String memo;
  private String comment;

}
