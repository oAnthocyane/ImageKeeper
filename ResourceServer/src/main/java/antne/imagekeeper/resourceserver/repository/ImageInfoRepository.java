package antne.imagekeeper.resourceserver.repository;

import antne.imagekeeper.resourceserver.model.ImageInfo;
import antne.imagekeeper.resourceserver.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageInfoRepository extends CrudRepository<ImageInfo, Long> {
    @Query("SELECT o.pathToImage from ImageInfo o where o.uniqPhrase = ?1 and o.user = ?2")
    Optional<String> findPathToImageByUniqPhraseAndUser(String uniqPhrase, User user);

    @Query("SELECT i.pathToImage FROM ImageInfo i WHERE i.user = :user" +
            " AND EXISTS (SELECT k FROM i.keysPhrase k WHERE k IN :keyPhrases)")
    Page<String> findByUserAndAnyKeyPhrase(List<String> keyPhrases, User user, Pageable pageable);

}
