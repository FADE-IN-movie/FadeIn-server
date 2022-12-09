package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.dao.LikeDAO;
import PINAMO.FADEIN.data.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public LikeEntity getLikeByUserIdAndContentId(Long userId, Long contentId) {
        return likeRepository.findByUserEntity_IdAndContentEntity_Id(userId, contentId);
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
    public Boolean isLikeByUserIdAndContentId(Long userId, Long contentId) {
        return likeRepository.existsByUserEntity_IdAndContentEntity_Id(userId, contentId);
    }
}
