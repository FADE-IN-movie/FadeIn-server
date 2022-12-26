package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

  ContentEntity getContentEntityByTmdbIdAndType(int tmdbId, String type);

  List<ContentEntity> findAllByTypeAndIsRecommended(String type, String isRecommended);
  List<ContentEntity> getContentEntitiesById(int contentId);

  Boolean existsByTmdbIdAndType(int tmdbId, String type);
  Boolean existsByType(String type);

}
