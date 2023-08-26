package antne.imagekeeper.resourceserver.controller;

import antne.imagekeeper.resourceserver.model.data.ImageInfoDTO;
import antne.imagekeeper.resourceserver.response.ResponseHandler;
import antne.imagekeeper.resourceserver.service.image.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageInfoController {

    private final ImageInfoService imageInfoService;

    @Autowired
    public ImageInfoController(ImageInfoService imageInfoService) {
        this.imageInfoService = imageInfoService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody ImageInfoDTO imageInfoDTO) throws IOException {
        long id = imageInfoService.save(imageInfoDTO);
        return ResponseHandler.responseBuilder("Image was added", HttpStatus.CREATED, id);
    }

    @GetMapping("/user/{userId}/findByUniqPhraseAndUser/{uniqPhrase}")
    public ResponseEntity<Object> findByUniqPhraseAndUser(@PathVariable String uniqPhrase, @PathVariable long userId)
            throws IOException{
        byte[] photo = imageInfoService.findByUniqPhraseAndUser(uniqPhrase, userId);
        return ResponseHandler.responseBuilder("Image was found", HttpStatus.OK, photo);
    }

    @GetMapping("/user/{userId}/findByKeysPhraseAndUser")
    public ResponseEntity<Object> findByKeysPhraseAndUser(@PathVariable long userId,
                                                          @RequestParam List<String> keysPhrase) throws IOException{
        List<byte[]> photos = imageInfoService.findByUserAndAnyKeyPhrase(keysPhrase, userId);
        return ResponseHandler.responseBuilder("Images was found only by user", HttpStatus.OK, photos);
    }

    @GetMapping("/user/{userId}/findByUniqPhraseAndGroup/{uniqPhrase}")
    public ResponseEntity<Object> findByUniqPhraseAndAllGroup(@PathVariable String uniqPhrase, @PathVariable long userId)
            throws IOException{
        List<byte[]> photos = imageInfoService.findByUniqPhraseAndGroup(uniqPhrase, userId);
        return ResponseHandler.responseBuilder("Images was found by groups and user", HttpStatus.OK, photos);
    }

    @GetMapping("/user/{userId}/findByUniqPhraseAndGroup/{uniqPhrase}/group")
    public ResponseEntity<Object> findByUniqPhraseAndGroup(
            @PathVariable String uniqPhrase, @PathVariable long userId, @RequestParam List<String> groupName
    ) throws IOException{
        List<byte[]> photos = imageInfoService.findByUniqPhraseAndGroup(uniqPhrase, userId, groupName);
        return ResponseHandler.responseBuilder("Images was found by groups and user", HttpStatus.OK, photos);
    }

    @GetMapping("/user/{userId}/findByKeyPhraseAndGroup")
    public ResponseEntity<Object> findByKeyPhraseAndAllGroup(@PathVariable long userId, @RequestParam List<String> keyPhrases)
            throws IOException{
        List<byte[]> photos = imageInfoService.findByKeyPhrasesAndGroup(keyPhrases, userId);
        return ResponseHandler.responseBuilder("Images was found by groups and user", HttpStatus.OK, photos);
    }

    @GetMapping("/user/{userId}/findByKeyPhraseAndGroup/group")
    public ResponseEntity<Object> findByUniqPhraseAndGroup(
            @PathVariable long userId, @RequestParam List<String> groupName, @RequestParam List<String> keyPhrases
    ) throws IOException{
        List<byte[]> photos = imageInfoService.findByKeyPhrasesAndGroup(keyPhrases, userId, groupName);
        return ResponseHandler.responseBuilder("Images was found by groups and user", HttpStatus.OK, photos);
    }
}


