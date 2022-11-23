package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

  ReviewEntity findByUserEntity_IdAndContentEntity_Id(Long userId, Long contentId);

}
