package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

  ContentEntity getContentEntityByTmdbId(int tmdbId);

  List<ContentEntity> findAllByType(String type);

  Boolean existsByTmdbId(int tmdbId);

}
