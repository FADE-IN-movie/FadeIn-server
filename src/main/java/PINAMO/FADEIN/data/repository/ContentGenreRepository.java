package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ContentGenreRepository extends JpaRepository<ContentGenreEntity, Long> {

  ArrayList<ContentGenreEntity> findAllByContentEntity_Id(Long contentId);

  @Query(value =
      "SELECT genre " +
          "FROM content_genres " +
          "WHERE content_id IN (" +
            "SELECT id " +
            "FROM contents " +
            "WHERE contents.type = :c_type AND id IN (" +
              "SELECT content_id " +
              "FROM likes " +
              "WHERE user_id IN (" +
                "SELECT id " +
                "FROM users " +
                "WHERE id = :user_id))) GROUP BY genre ORDER BY COUNT(*) desc LIMIT 1;", nativeQuery = true)
  String getPreferenceGenre(@Param("user_id") Long userId, @Param("c_type") String type);
}
