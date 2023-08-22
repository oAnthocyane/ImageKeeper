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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ImageInfoServiceImpl implements ImageInfoService{

    private final int MAX_PAGE_REQUEST = 5;

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
        long userId = imageInfoDTO.getUserId();
        User user = userService.findByUserId(userId);
        if(imageInfoRepository.findPathToImageByUniqPhraseAndUser(imageInfoDTO.getUniqPhrase(),
                        user).isPresent())
            throw new ObjectExistException("This image already exist", ModelType.ImageInfo);

        List<Group> groupsForImage = new ArrayList<>();
        List<String> groups = imageInfoDTO.getGroupsName();
        for (String groupName : groups){
            Group group = groupService.findByName(groupName);
            if(!group.getUsers().contains(user)) throw new ObjectNotFoundException("This group is not found",
                    ModelType.Group);
            // TODO: exception if in this group exist image for this uniqPhrase
            else groupsForImage.add(group);
        }

        String uniqPhrase = imageInfoDTO.getUniqPhrase();
        if(!findByUniqPhraseAndGroup(uniqPhrase, userId, groups).isEmpty())
            throw new ObjectExistException("Uniq phrase image was exist in this group", ModelType.ImageInfo);

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

        if(imageTemp.exists()) imageTemp.delete();

        return id;
    }

    @Override
    public byte[] findByUniqPhraseAndUser(String uniqPhrase, long userId) throws IOException{
        Optional<String> pathOptional = imageInfoRepository
                .findPathToImageByUniqPhraseAndUser(uniqPhrase, userService.findByUserId(userId));
        if(pathOptional.isEmpty()) throw new ObjectNotFoundException("This image not exist", ModelType.ImageInfo);

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
        User user = userService.findByUserId(userId);
        List<Group> groups = user.getGroups();
        Page<String> imagesPath = imageInfoRepository
                .findByUniqPhraseAndGroupAndUser(uniqPhrase, groups, user, RANGE_CONTENT);

        return downloadPhotosFromPage(imagesPath);
    }

    @Override
    public List<byte[]> findByUniqPhraseAndGroup(String uniqPhrase, long userId, List<String> groupNames) throws IOException {
        User user = userService.findByUserId(userId);

        List<Group> groups = getSelectedGroups(user, groupNames);

        Page<String> imagesPath = imageInfoRepository
                .findByUniqPhraseAndGroupAndUser(uniqPhrase, groups, user, RANGE_CONTENT);

        return downloadPhotosFromPage(imagesPath);
    }

    @Override
    public List<byte[]> findByKeyPhrasesAndGroup(List<String> keyPhrases, long userId) throws IOException {
        User user = userService.findByUserId(userId);
        List<Group> groups = user.getGroups();
        Page<String> imagePath = imageInfoRepository
                .findByKeysPhraseAndGroupAndUser(keyPhrases, groups, user, RANGE_CONTENT);
        return downloadPhotosFromPage(imagePath);
    }

    @Override
    public List<byte[]> findByKeyPhrasesAndGroup(List<String> keyPhrases, long userId, List<String> groupNames)
            throws IOException {
        User user = userService.findByUserId(userId);

        List<Group> selectedGroups = getSelectedGroups(user, groupNames);

        Page<String> imagePaths = imageInfoRepository
                .findByKeysPhraseAndGroupAndUser(keyPhrases, selectedGroups, user, RANGE_CONTENT);
        return downloadPhotosFromPage(imagePaths);
    }

    private List<byte[]> downloadPhotosFromPage(Page<String> imagesPath) throws IOException{
        List<byte[]> photos = new ArrayList<>();
        List<String> pathImages = imagesPath.getContent();
        for(String pathImage: pathImages){
            byte[] photo = googleDriveDownloader.downloadFileBytesFromDrive(pathImage);
            photos.add(photo);
        }

        return photos;
    }

    private List<Group> getSelectedGroups(User user, List<String> groupNames){
        Set<Group> groups = new HashSet<>();
        for(String groupName : groupNames){
            groups.add(groupService.findByName(groupName));
        }
        Set<Group> userGroups = new HashSet<>(user.getGroups());
        if(!userGroups.containsAll(groups))
            throw new ObjectNotFoundException("This group is not exist for this user", ModelType.Group);
        return groups.stream().toList();
    }
}
