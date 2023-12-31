package antne.imagekeeper.resourceserver.service.image;

import antne.imagekeeper.resourceserver.exception.object.ObjectExistException;
import antne.imagekeeper.resourceserver.exception.object.ObjectNotFoundException;
import antne.imagekeeper.resourceserver.google.drive.api.GoogleDriveDownloader;
import antne.imagekeeper.resourceserver.google.drive.api.GoogleDriveUploader;
import antne.imagekeeper.resourceserver.model.Group;
import antne.imagekeeper.resourceserver.model.ImageInfo;
import antne.imagekeeper.resourceserver.model.ModelType;
import antne.imagekeeper.resourceserver.model.User;
import antne.imagekeeper.resourceserver.model.data.ImageInfoDTO;
import antne.imagekeeper.resourceserver.repository.ImageInfoRepository;
import antne.imagekeeper.resourceserver.service.group.GroupService;
import antne.imagekeeper.resourceserver.service.user.UserService;
import antne.imagekeeper.resourceserver.utils.ImageCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ImageInfoServiceImpl implements ImageInfoService {

    private final int MAX_PAGE_REQUEST = 7;

    private final Pageable RANGE_CONTENT = PageRequest.of(0, MAX_PAGE_REQUEST);

    private final UserService userService;
    private final GroupService groupService;
    private final GoogleDriveDownloader googleDriveDownloader;
    private final GoogleDriveUploader googleDriveUploader;
    private final ImageInfoRepository imageInfoRepository;

    @Autowired
    public ImageInfoServiceImpl(UserService userService, GroupService groupService,
                                GoogleDriveDownloader googleDriveDownloader,
                                GoogleDriveUploader googleDriveUploader,
                                ImageInfoRepository imageInfoRepository) {
        this.userService = userService;
        this.groupService = groupService;
        this.googleDriveDownloader = googleDriveDownloader;
        this.googleDriveUploader = googleDriveUploader;
        this.imageInfoRepository = imageInfoRepository;
    }

    @Override
    public long save(ImageInfoDTO imageInfoDTO) throws IOException {
        log.info("Saving image info for user: {}", imageInfoDTO.getUserId());
        long userId = imageInfoDTO.getUserId();
        User user = userService.findByUserId(userId);
        if (imageInfoRepository.findPathToImageByUniqPhraseAndUser(imageInfoDTO.getUniqPhrase(),
                user).isPresent()) {
            log.error("Image with unique phrase already exists: {}", imageInfoDTO.getUniqPhrase());
            throw new ObjectExistException("This image already exist", ModelType.ImageInfo);
        }

        List<String> groups = imageInfoDTO.getGroupsName();
        List<Group> groupsForImage = getSelectedGroups(user, groups);

        String uniqPhrase = imageInfoDTO.getUniqPhrase();
        if (!findByUniqPhraseAndGroup(uniqPhrase, userId, groups).isEmpty()) {
            log.error("Unique phrase image already exists in this group: {}", uniqPhrase);
            throw new ObjectExistException("Uniq phrase image was exist in this group", ModelType.ImageInfo);
        }

        byte[] imageBytes = imageInfoDTO.getPhoto();
        File imageTemp = ImageCreator.createTemporaryImageFile(imageBytes, "jpg");
        String folderId = googleDriveUploader.uploadImageToDrive(imageTemp, imageInfoDTO.getUniqPhrase(),
                "image/jpeg", String.valueOf(imageInfoDTO.getUserId()));

        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setUniqPhrase(imageInfoDTO.getUniqPhrase());
        imageInfo.setKeysPhrase(imageInfoDTO.getKeysPhrase());
        imageInfo.setUser(user);
        imageInfo.setGroups(groupsForImage);
        imageInfo.setPathToImage(folderId);

        long id = imageInfoRepository.save(imageInfo).getId();

        if (imageTemp.exists()) imageTemp.delete();

        log.info("Image info saved successfully with id: {}", id);
        return id;
    }

    @Override
    public byte[] findByUniqPhraseAndUser(String uniqPhrase, long userId) throws IOException {
        Optional<String> pathOptional = imageInfoRepository
                .findPathToImageByUniqPhraseAndUser(uniqPhrase, userService.findByUserId(userId));
        if (pathOptional.isEmpty()) throw new ObjectNotFoundException("This image not exist", ModelType.ImageInfo);

        String pathToImage = pathOptional.get();

        return googleDriveDownloader.downloadFileBytesFromDrive(pathToImage);
    }

    @Override
    public List<byte[]> findByUserAndAnyKeyPhrase(List<String> keysPhrase, long userId) throws IOException {
        User user = userService.findByUserId(userId);
        Page<String> imagesPath = imageInfoRepository.findByUserAndAnyKeyPhrase(keysPhrase, user, RANGE_CONTENT);

        return downloadPhotosFromPage(imagesPath);

    }

    @Override
    public List<byte[]> findByUniqPhraseAndGroup(String uniqPhrase, long userId) throws IOException {
        log.info("User {} find by key uniq phrase: {} in all groups", userId, uniqPhrase);

        User user = userService.findByUserId(userId);
        List<Group> groups = user.getGroups();
        Page<String> imagesPath = imageInfoRepository
                .findByUniqPhraseAndGroupAndUser(uniqPhrase, groups, user, RANGE_CONTENT);

        return downloadPhotosFromPage(imagesPath);
    }

    @Override
    public List<byte[]> findByUniqPhraseAndGroup(String uniqPhrase, long userId, List<String> groupNames) throws IOException {
        log.info("User {} find by key uniq phrase: {} in groups: {}", userId, uniqPhrase, groupNames);
        User user = userService.findByUserId(userId);

        List<Group> groups = getSelectedGroups(user, groupNames);

        Page<String> imagesPath = imageInfoRepository
                .findByUniqPhraseAndGroupAndUser(uniqPhrase, groups, user, RANGE_CONTENT);

        return downloadPhotosFromPage(imagesPath);
    }

    @Override
    public List<byte[]> findByKeyPhrasesAndGroup(List<String> keyPhrases, long userId) throws IOException {
        log.info("User {} find by key phrases: {} in all groups", userId, keyPhrases);

        User user = userService.findByUserId(userId);
        List<Group> groups = user.getGroups();
        Page<String> imagePath = imageInfoRepository
                .findByKeysPhraseAndGroupAndUser(keyPhrases, groups, user, RANGE_CONTENT);
        return downloadPhotosFromPage(imagePath);
    }

    @Override
    public List<byte[]> findByKeyPhrasesAndGroup(List<String> keyPhrases, long userId, List<String> groupNames)
            throws IOException {
        log.info("User: {} find by key phrases: {} in groups: {}", userId, keyPhrases, groupNames);
        User user = userService.findByUserId(userId);

        List<Group> selectedGroups = getSelectedGroups(user, groupNames);

        Page<String> imagePaths = imageInfoRepository
                .findByKeysPhraseAndGroupAndUser(keyPhrases, selectedGroups, user, RANGE_CONTENT);
        return downloadPhotosFromPage(imagePaths);
    }

    private List<byte[]> downloadPhotosFromPage(Page<String> imagesPath) throws IOException {
        List<byte[]> photos = new ArrayList<>();
        List<String> pathImages = imagesPath.getContent();
        for (String pathImage : pathImages) {
            byte[] photo = googleDriveDownloader.downloadFileBytesFromDrive(pathImage);
            photos.add(photo);
        }

        return photos;
    }

    private List<Group> getSelectedGroups(User user, List<String> groupNames) {
        log.info("Selected groups: {} for user {}", groupNames, user.getUserId());
        Set<Group> groups = new HashSet<>();
        for (String groupName : groupNames) {
            groups.add(groupService.findByName(groupName));
        }
        Set<Group> userGroups = new HashSet<>(user.getGroups());
        if (!userGroups.containsAll(groups)) {
            log.error("Selected groups do not exist for user: {}", user.getUserId());
            throw new ObjectNotFoundException("This group is not exist for this user", ModelType.Group);
        }
        return groups.stream().toList();
    }
}
