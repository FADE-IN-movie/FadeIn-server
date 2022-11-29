package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.dao.ContentGenreDAO;
import PINAMO.FADEIN.data.dao.LikeDAO;
import PINAMO.FADEIN.data.repository.ContentGenreRepository;
import PINAMO.FADEIN.data.repository.ContentRepository;
import PINAMO.FADEIN.data.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentGenreDAOImpl implements ContentGenreDAO {

    ContentGenreRepository contentGenreRepository;

    @Autowired
    public ContentGenreDAOImpl(ContentGenreRepository contentGenreRepository){
        this.contentGenreRepository = contentGenreRepository;
    }

    @Override
    public ContentGenreEntity saveContentGenre(ContentGenreEntity contentGenreEntity) {
        return contentGenreRepository.save(contentGenreEntity);
    }

    @Override
    public ArrayList<ContentGenreEntity> getContentGenresByContentId(Long contentId) {
        return contentGenreRepository.findAllByContentEntity_Id(contentId);
    }

    @Override
    public String getPreferenceGenreByUserId(Long userId, String type) {
        return contentGenreRepository.getPreferenceGenre(userId, type);
    }
}
