package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<RecommendEntity, Long> {
}
