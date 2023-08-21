package antne.imagekeeper.resourceserver.service.image;


import antne.imagekeeper.resourceserver.model.data.ImageInfoDTO;

import java.io.IOException;
import java.util.List;

public interface ImageInfoService {
    long save(ImageInfoDTO imageInfo) throws IOException;

    byte[] findByUniqPhraseAndUser(String uniqPhrase, long userId) throws IOException;

    List<byte[]> findByUserAndAnyKeyPhrase(List<String> keysPhrases, long userId) throws IOException;
}
