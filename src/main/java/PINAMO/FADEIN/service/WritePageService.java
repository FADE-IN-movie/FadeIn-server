package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.WritePageDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;

import java.util.Map;

public interface WritePageService {

  WriteContentObject getWritePage(String path);

  Map<String, Long> writeReview(Long userId, WritePageDTO writePageDTO);

}
