package PINAMO.FADEIN.data.repository;

import PINAMO.FADEIN.data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity getByUserEmail(String userEmail);

  boolean existsById(Long userId);

}
