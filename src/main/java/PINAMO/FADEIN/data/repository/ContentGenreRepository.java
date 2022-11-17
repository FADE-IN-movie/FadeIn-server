package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ContentGenreRepository extends JpaRepository<ContentGenreEntity, Long> {

  ArrayList<ContentGenreEntity> findAllByContentEntity_Id(Long contentId);

}
