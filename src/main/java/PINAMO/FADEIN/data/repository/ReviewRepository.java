package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {

  boolean existsById(String reviewId);
  boolean existsByUserEntity_Id(Long userId);

  ReviewEntity findByUserEntity_IdAndContentEntity_Id(Long userId, Long contentId);

  List<ReviewEntity> findAllByUserEntity_Id(Long userId);

  void deleteById(String reviewId);

  @Query(value = "SELECT * " +
      "FROM reviews AS r " +
      "WHERE r.user_id = :user_id AND (r.watched_date BETWEEN :start_date AND :end_date);", nativeQuery = true)
  List<ReviewEntity> findAllByUserIdAnAndWatchedDate(@Param("user_id") Long userId, @Param("start_date") String start, @Param("end_date") String end);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE ReviewEntity r SET r.watchedDate = :watchedDate, r.watchedTime = :watchedTime, r.watchedIn = :watchedIn, r.watchedWith = :watchedWith, r.rating = :rating, r.memo = :memo, r.comment = :comment WHERE r.id = :reviewId")
  int updateReview(String reviewId, String watchedDate, String watchedTime, String watchedIn, String watchedWith, float rating, String memo, String comment);

}
