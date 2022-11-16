package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

  LikeEntity findByUserEntity_IdAndContentEntity_TmdbId(Long userId, int tmdbId);

  LikeEntity getLikeEntityByContentEntity(ContentEntity contentEntity);

  Boolean existsByUserEntity_IdAndContentEntity_TmdbId(Long userId, int tmdbId);
}
