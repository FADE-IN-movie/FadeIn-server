package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

  ContentEntity getContentEntityByTmdbId(int tmdbId);

  Boolean existsByTmdbId(int tmdbId);
}
