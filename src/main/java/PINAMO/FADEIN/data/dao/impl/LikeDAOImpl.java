package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dao.LikeDAO;
import PINAMO.FADEIN.data.dao.UserDAO;
import PINAMO.FADEIN.data.repository.LikeRepository;
import PINAMO.FADEIN.data.repository.UserRepository;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikeDAOImpl implements LikeDAO {

    LikeRepository likeRepository;

    @Autowired
    public LikeDAOImpl(LikeRepository likeRepository){
        this.likeRepository = likeRepository;
    }


    @Override
    public LikeEntity saveLike(LikeEntity likeEntity) {
        likeRepository.save(likeEntity);
        return likeEntity;
    }

    @Override
    public LikeEntity getLike(Long userId, int tmdbId) {
        return likeRepository.findByUserEntity_IdAndContentEntity_TmdbId(userId, tmdbId);
    }

    @Override
    public List<LikeEntity> getLikesByUserId(Long userId) {
        return likeRepository.findAllByUserEntity_Id(userId);
    }

    @Override
    public int deleteLike(LikeEntity likeEntity) {
        likeRepository.delete(likeEntity);
        return 1;
    }

    @Override
    public Boolean isLikeByUserIdAndTmdbId(Long userId, int tmdbId) {
        return likeRepository.existsByUserEntity_IdAndContentEntity_TmdbId(userId, tmdbId);
    }
}
