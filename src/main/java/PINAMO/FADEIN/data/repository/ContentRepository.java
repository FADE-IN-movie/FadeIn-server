package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

  ContentEntity getContentEntityByTmdbIdAndType(int tmdbId, String type);

  List<ContentEntity> findAllByType(String type);
  List<ContentEntity> getContentEntitiesById(int contentId);

  Boolean existsByTmdbIdAndType(int tmdbId, String type);

}
