package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {

  boolean existsById(String reviewId);

  ReviewEntity findByUserEntity_IdAndContentEntity_Id(Long userId, Long contentId);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE ReviewEntity r SET r.watched_date = :watched_date, r.watched_time = :watched_time, r.watched_in = :watched_in, r.watched_with = :watched_with, r.rating = :rating, r.memo = :memo, r.comment = :comment WHERE r.id = :reviewId")
  int updateReview(String reviewId, String watched_date, String watched_time, String watched_in, String watched_with, float rating, String memo, String comment);

}
